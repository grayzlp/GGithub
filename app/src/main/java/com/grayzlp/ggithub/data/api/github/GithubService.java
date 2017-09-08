package com.grayzlp.ggithub.data.api.github;

/**
 * Github API - https://developer.github.com/v3/
 */

public interface GithubService {

    String ENDPOINT = "https://api.github.com";
    String DATE_FORMAT = "yyyy/MM/ddTHH:mm:ssZ";
    int PER_PAGE_MAX = 100;
    int PER_PAGE_DEFAULT = 30;

}
