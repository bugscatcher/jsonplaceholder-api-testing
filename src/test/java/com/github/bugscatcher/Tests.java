package com.github.bugscatcher;

import com.github.bugscatcher.dto.CommentsDTO;
import com.github.bugscatcher.dto.PostDTO;
import com.github.bugscatcher.dto.UserDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.github.bugscatcher.APIHelper.*;
import static com.github.bugscatcher.TestUtil.*;

public class Tests extends Abstract {
    @Test
    public void checkEmailsFormat_oneUsername() {
        String property = "username.samantha";
        String username = properties.getProperty(property);
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);
        checkEmailsFormat(username);
    }

    @Test
    public void checkEmailsFormat_multipleUsername() {
        String property = "username.multiple";
        String username = properties.getProperty(property);
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);
        checkEmailsFormat(username.split(","));
    }

    @Test
    public void checkEmailsFormat_incorrectEmail() {
//        Here I'm using ready-made data from db.json. This can be replaced by creating a user, post and comments through requests.
        String property = "username.negative";
        String username = properties.getProperty(property);
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);
        checkEmailsFormat(username);
    }

    private void checkEmailsFormat(String[] usernames) {
        Arrays.stream(usernames).forEach(this::checkEmailsFormat);
    }

    private void checkEmailsFormat(String username) {
        UserDTO user = searchUser(username);
        Assert.assertNotNull(getMessageForNonExistentUser(username), user);

        PostDTO[] posts = getPosts(user.getId());
        checkComments(posts);
    }

    private void checkComments(PostDTO[] posts) {
        Arrays.stream(posts)
                .forEach(post -> {
                    CommentsDTO[] comments = getComments(post.getId());
                    Arrays.stream(comments)
                            .forEach(comment -> {
                                boolean isValid = TestUtil.isValid(comment.getEmail());
                                Assert.assertTrue(getMessageForEmailInvalidFormat(comment.getEmail()), isValid);
                            });
                });
    }
}
