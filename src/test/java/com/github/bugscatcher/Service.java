package com.github.bugscatcher;

import com.github.bugscatcher.dto.CommentsDTO;
import com.github.bugscatcher.dto.PostDTO;
import com.github.bugscatcher.dto.UserDTO;

import java.util.Arrays;

import static com.github.bugscatcher.Abstract.LOG;
import static com.github.bugscatcher.Abstract.getResource;

class Service {
    static UserDTO searchUser(String username) {
        UserDTO[] users = getResource(EndPoints.USERS, UserDTO[].class);
        return Arrays.stream(users)
                .filter(user -> username.equals(user.getUsername()))
                .findAny()
                .orElse(null);
    }

    static PostDTO[] getPosts(int userID) {
        PostDTO[] posts = getResource(EndPoints.POSTS + userID, PostDTO[].class);
        if (posts.length == 0) {
            LOG.warn("No posts for userId=" + userID);
        }
        return posts;
    }

    static CommentsDTO[] getComments(int postID) {
        CommentsDTO[] comments = getResource(EndPoints.COMMENTS + postID, CommentsDTO[].class);
        if (comments.length == 0) {
            LOG.warn("No comments for postId=" + postID);
        }
        return comments;
    }
}
