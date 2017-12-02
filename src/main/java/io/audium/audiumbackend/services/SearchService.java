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
  @Autowired
  private GenreRepository    genreRepository;

  private String buildRelevanceLevelStatement(String searchQuery, String entityAlias, String searchColumn) {
    String             column         = entityAlias + "." + searchColumn;
    ArrayList<String>  searchTerms    = new ArrayList();
    ArrayList<Integer> termBaseValues = new ArrayList<>(); // Base relevance value of each search term
    /* Replace escape characters with spaces */
    searchQuery = searchQuery.replaceAll("[\t\n\r\b\f]", " ");
    /* Insert backslashes to escape characters that interfere with Regular Expressions */
    searchQuery = searchQuery.replaceAll("(\\[|\\]|\\.|\\(|\\)|\\<|\\>|\\{|\\}|\\\\|\\^|\\-|\\=|\\$|\\!|\\||\\?|\\*|\\+)", "\\\\$1");
    /* Create a list of search terms from the full search string: */
    /*   Use RegEx to split search string */
    Matcher searchTermMatcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(searchQuery); // Split on spaces, except for quoted substrings
    while (searchTermMatcher.find()) {
      int    value; // Base relevance value of current token
      String token = searchTermMatcher.group(1).replaceAll("^\"|\"$", ""); // Remove quotes from quoted substrings
      value = token.replaceAll("\\\\", "").length();
      /* Backslashes must be quadruple-escaped to account for Java string interpreter, MySQL string interpreter, and MySQL RegEx interpreter */
      token = token.replaceAll("\\\\", "\\\\\\\\");
      /* Add each search term to the list of search terms, but only non-empty strings that aren't already in the list */
      if (token.length() > 0 && !searchTerms.contains(token)) {
        searchTerms.add(token);
        termBaseValues.add(value);
      }
    }

    /* Build an SQL statement to check for relevancy level (to sort search results by most relevant) */
    String relevanceLevelStatement = "( ";
    for (int i = 0; i < searchTerms.size(); i++) {  // Can't use for-each syntax here because we need quick access to the list index
      String term  = searchTerms.get(i);
      int    value = termBaseValues.get(i);
      if (i > 0) {
        relevanceLevelStatement += " + ";
      }
      //relevanceLevelStatement += "IF(" + entityAlias + "." + searchColumn + " REGEXP \"(^| )" + term + "( |$)\", " + Integer.toString(term.length() + 1)
      //  + ", IF(" + entityAlias + "." + searchColumn + " REGEXP \"(^| )" + term + "\", " + Integer.toString(term.length()) + ", 0))";
      relevanceLevelStatement += "IF(" + column + " REGEXP \"^" + term + "$\", " + (value + 2)
        + ", IF(" + column + " REGEXP \"(^|[[:blank:]]|[[:punct:]])" + term + "([[:blank:]]|[[:punct:]]|$)\", " + (value + 1)
        + ", IF(" + column + " REGEXP \"(^|[[:blank:]]|[[:punct:]])" + term + "\", " + value + ", 0)))";
    }

    relevanceLevelStatement += " ) AS Relevance";
    return relevanceLevelStatement;
  }

  public List<Song> searchSongs(String searchQuery) {
    String relevanceLevelStatement = buildRelevanceLevelStatement(searchQuery, "S", "title");

    /* Build full query */
    String query = "SELECT Se.songId AS songId, Se.title AS title, Se.duration AS duration, Se.year AS year, Se.isExplicit AS isExplicit, "
      + " Art.artistId AS artistId, Art.name AS artistName, Alb.albumId AS albumId, Alb.title AS albumTitle, Se.file AS file, G.genreId AS genreId, G.name AS genreName FROM "
      + " (SELECT S.*, " + relevanceLevelStatement + " FROM Song AS S) AS Se, Artist AS Art, artist_song AS ArtS, Album AS Alb, album_song AS AlbS, Genre AS G "
      + " WHERE Se.Relevance > 0 AND G.genreId = Se.genreId AND ArtS.songId = Se.songId AND ArtS.artistId = Art.artistId AND ArtS.isPrimaryArtist = TRUE AND AlbS.songId = Se.songId AND AlbS.albumId = Alb.albumId "
      + " GROUP BY Se.songId ORDER BY Se.Relevance DESC";
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
    String query = "SELECT Se.accountId AS accountId, Se.username AS username, Se.role AS role, Se.bio AS bio, Se.followerCount AS followerCount "
      + " FROM (SELECT C.*, Cust.bio, COUNT(CF.accountId) AS followerCount, " + relevanceLevelStatement + " FROM Account C, Customer Cust, UserPreferences P, customer_follower CF "
      + " WHERE C.accountId = Cust.accountId AND C.accountId = P.accountId AND P.publicProfile = TRUE AND CF.accountId = Cust.accountId AND (C.role = \"BasicUser\" OR C.role = \"PremiumUser\") GROUP BY CF.accountId) AS Se "
      + " WHERE Se.Relevance > 0 ORDER BY Se.Relevance DESC";
    return customerRepository.searchCustomers(query);
  }

  public List<Genre> searchGenres(String searchQuery) {
    String relevanceLevelStatement = buildRelevanceLevelStatement(searchQuery, "G", "name");

    /* Build full query */
    String query = "SELECT Se.genreId AS genreId, Se.name AS genreName "
      + " FROM (SELECT G.*, " + relevanceLevelStatement + " FROM Genre G) AS Se "
      + " WHERE Se.Relevance > 0 ORDER BY Se.Relevance DESC";
    return genreRepository.searchGenres(query);
  }
}
