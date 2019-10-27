package com.github.bugscatcher;

import com.github.bugscatcher.dto.CommentsDTO;
import com.github.bugscatcher.dto.PostDTO;
import com.github.bugscatcher.dto.UserDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.github.bugscatcher.TestUtil.*;

public class Tests extends Abstract {
    @Test
    public void checkEmailsFormat() {
        String property = "username";
        String username = properties.getProperty(property);
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);

        UserDTO user = searchUser(username);
        Assert.assertNotNull(getMessageForNonExistentUser(username), user);

        PostDTO[] posts = getResource(EndPoints.POSTS + "?userId=" + user.getId(), PostDTO[].class);
        Arrays.stream(posts)
                .forEach(post -> {
                    CommentsDTO[] comments = getResource(EndPoints.COMMENTS + "?postId=" + post.getId(), CommentsDTO[].class);
                    Arrays.stream(comments)
                            .forEach(comment -> {
                                boolean isValid = TestUtil.isValid(comment.getEmail());
                                Assert.assertTrue(getMessageForEmailInvalidFormat(comment.getEmail()), isValid);
                            });
                });
    }
}
