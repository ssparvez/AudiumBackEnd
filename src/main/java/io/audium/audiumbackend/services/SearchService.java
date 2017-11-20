package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.*;
import io.audium.audiumbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SearchService {

  @Autowired
  private SongRepository     songRepository;
  @Autowired
  private AlbumRepository    albumRepository;
  @Autowired
  private ArtistRepository   artistRepository;
  @Autowired
  private PlaylistRepository playlistRepository;
  @Autowired
  private EventRepository    eventRepository;
  @Autowired
  private CustomerRepository customerRepository;

  private String buildRelevanceLevelStatement(String searchQuery, String entityAlias, String searchColumn) {
    ArrayList<String> searchTerms = new ArrayList();

    /* Replace annoying escape characters with spaces */
    searchQuery.replaceAll("[\t\n\r\b\f]", " ");
    /* Create a list of search terms from the full search string: */
    /*   Use RegEx to split search string */
    Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(searchQuery); // Split on spaces, except for quoted substrings
    while (matcher.find()) {
      String token = matcher.group(1).replaceAll("^\"|\"$", ""); // Remove quotes from quoted substrings
      /* Add each search term to the list of search terms, but only non-empty strings that aren't already in the list */
      if (token.length() > 0 && !searchTerms.contains(token)) {
        searchTerms.add(token);
      }
    }

    /* Build an SQL statement to check for relevancy level (to sort search results by most relevant) */
    String relevanceLevelStatement = "( ";
    for (int i = 0; i < searchTerms.size(); i++) {  // Can't use for-each syntax here because we need quick access to the list index
      String term = searchTerms.get(i);
      if (i > 0) {
        relevanceLevelStatement += " + ";
      }
      relevanceLevelStatement += "IF(" + entityAlias + "." + searchColumn + " REGEXP \"(^| )" + term + "( |$)\", " + Integer.toString(term.length() + 1)
        + ", IF(" + entityAlias + "." + searchColumn + " REGEXP \"(^| )" + term + "\", " + Integer.toString(term.length()) + ", 0))";
    }

    relevanceLevelStatement += " ) AS Relevance";
    return relevanceLevelStatement;
  }

  public List<Song> searchSongs(String searchQuery) {
    String relevanceLevelStatement = buildRelevanceLevelStatement(searchQuery, "S", "title");

    /* Build full query */
    String query = "SELECT Se.songId AS songId, Se.title AS title, Se.duration AS duration, Se.year AS year, Se.isExplicit AS isExplicit,"
      + " Se.lyrics AS lyrics, G.genreId AS genreId, G.name AS genreName FROM (SELECT S.*, " + relevanceLevelStatement
      + " FROM Song S) AS Se, Genre AS G WHERE Se.Relevance > 0 AND G.genreId = Se.genreId ORDER BY Se.Relevance DESC";

    return songRepository.searchSongs(query);
  }

  public List<Album> searchAlbums(String searchQuery) {
    String relevanceLevelStatement = buildRelevanceLevelStatement(searchQuery, "A", "title");

    /* Build full query */
    String query = "SELECT Se.albumId AS albumId, Se.title AS albumTitle, Se.year AS year, Se.artistId AS artistId, Art.name AS artistName"
      + " FROM (SELECT A.*, " + relevanceLevelStatement + " FROM Album A) AS Se, Artist AS Art "
      + " WHERE Se.Relevance > 0 AND Art.artistId = Se.artistId ORDER BY Se.Relevance DESC";

    return albumRepository.searchAlbums(query);
  }

  public List<Artist> searchArtists(String searchQuery) {
    String relevanceLevelStatement = buildRelevanceLevelStatement(searchQuery, "A", "name");

    /* Build full query */
    String query = "SELECT Se.artistId AS artistId, Se.name AS artistName, Se.bio AS bio "
      + " FROM (SELECT A.*, " + relevanceLevelStatement + " FROM Artist A) AS Se "
      + " WHERE Se.Relevance > 0 ORDER BY Se.Relevance DESC";

    return artistRepository.searchArtists(query);
  }

  public List<Playlist> searchPlaylists(String searchQuery) {
    String relevanceLevelStatement = buildRelevanceLevelStatement(searchQuery, "P", "name");

    /* Build full query */
    String query = "SELECT Se.playlistId AS playlistId, Se.name AS name, Se.description AS description, Se.isPublic AS isPublic, "
      + " Se.accountId AS accountId, A.username AS username, A.role AS role FROM (SELECT P.*, " + relevanceLevelStatement + " FROM Playlist P WHERE P.isPublic) AS Se, "
      + " Account AS A WHERE Se.Relevance > 0 AND A.accountId = Se.accountId ORDER BY Se.Relevance DESC";

    return playlistRepository.searchPlaylists(query);
  }

  public List<Event> searchEvents(String searchQuery) {
    String relevanceLevelStatement = buildRelevanceLevelStatement(searchQuery, "E", "title");

    /* Build full query */
    String query = "SELECT Se.eventId AS eventId, Se.title AS eventTitle, Se.eventDate AS eventDate, Se.isCancelled AS isCancelled, Se.description AS description "
      + " FROM (SELECT E.*, " + relevanceLevelStatement + " FROM Event E) AS Se "
      + " WHERE Se.Relevance > 0 ORDER BY Se.Relevance DESC";

    return eventRepository.searchEvents(query);
  }

  public List<Customer> searchCustomers(String searchQuery) {
    String relevanceLevelStatement = buildRelevanceLevelStatement(searchQuery, "C", "username");

    /* Build full query */
    String query = "SELECT Se.accountId AS accountId, Se.username AS username, Se.role AS role, Se.bio AS bio "
      + " FROM (SELECT C.*, Cust.bio, " + relevanceLevelStatement + " FROM Account C, Customer Cust WHERE C.accountId = Cust.accountId AND (C.role = \"BasicUser\" OR C.role = \"PremiumUser\")) AS Se "
      + " WHERE Se.Relevance > 0 ORDER BY Se.Relevance DESC";
    
    return customerRepository.searchCustomers(query);
  }
}