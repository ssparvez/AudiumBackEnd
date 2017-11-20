package io.audium.audiumbackend.services;

import java.util.ArrayList;
import java.util.List;

public class HelperService {

  public static <T> List<T> getResultsPage(int pageIndex, int pageSize, List<T> results) {
    int startIndex = (pageIndex * pageSize);
    int endIndex   = startIndex + pageSize;

    if (startIndex < 0 || pageSize <= 0) {
      return null;
    } else if (startIndex >= results.size()) {
      return new ArrayList<>();
    } else if (endIndex >= results.size()) {
      return results.subList(startIndex, (results.size() - startIndex));
    }
    return results.subList(startIndex, endIndex);
  }
}
