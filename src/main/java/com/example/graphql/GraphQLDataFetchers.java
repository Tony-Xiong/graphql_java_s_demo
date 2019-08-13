package com.example.graphql;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import lombok.extern.java.Log;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xiongyizhou on 2019/8/5 15:09
 * E-mail: xiongyizhou@powerpms.com
 *
 * @author xiongyizhou
 */
@Log
@Component
public class GraphQLDataFetchers {

    private static List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id", "book-1",
                    "name", "Harry Potter and the Philosopher's Stone",
                    "pageCount", "223",
                    "authorId", "author-1"),
            ImmutableMap.of("id", "book-2",
                    "name", "Moby Dick",
                    "pageCount", "635",
                    "authorId", "author-2"),
            ImmutableMap.of("id", "book-3",
                    "name", "Interview with the vampire",
                    "pageCount", "371",
                    "authorId", "author-3")
    );

    private static List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id", "author-1",
                    "firstName", "Joanne",
                    "lastName", "Rowling"),
            ImmutableMap.of("id", "author-2",
                    "firstName", "Herman",
                    "lastName", "Melville"),
            ImmutableMap.of("id", "author-3",
                    "firstName", "Anne",
                    "lastName", "Rice")
    );

    public DataFetcher getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return books
                    .stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }


    public DataFetcher getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher createBook() {
    return dfe -> {
      Map<String, Object> b = dfe.getArguments();
      Map<String,String> nb = new HashMap<>();
      Map<String,String> na = new HashMap<>();
        String bookId = UUID.randomUUID().toString();
      nb.put("id",bookId);
      nb.put("name",b.get("name").toString());
      nb.put("pageCount",b.get("pageCount").toString());
      int authorId = authors.size() +1;
      nb.put("authorId","author-"+ authorId);
      na.put("id","author-"+ authorId);
      na.put("firstName",b.get("firstName").toString());
      na.put("lastName",b.get("lastName").toString());

      List<Map<String, String>> nbooks = new ArrayList<>(books);
      List<Map<String, String>> nauthors = new ArrayList<>(authors);

      nbooks.add(nb);
      nauthors.add(na);

      books = nbooks;
      authors = nauthors;

      return nbooks.stream().filter(book -> book.get("id").equals(bookId)).findFirst().orElse(null);
    };
    }

    public DataFetcher getBooks() {
        return dfe -> {
            Integer count =  dfe.getArgument("minCount");
            return books.stream().filter(b -> Integer.valueOf(b.get("pageCount"))>count).collect(Collectors.toList());
        };
    }

    public DataFetcher<Publisher> subFun() {
    return dfe -> {
          System.out.println("DF");
          System.out.println(dfe.getArgument("channel").toString());
          log.info("你好啊！");
          return null;
        };
    }
}
