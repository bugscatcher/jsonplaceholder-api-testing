package com.github.bugscatcher;

import com.github.bugscatcher.dto.CommentsDTO;
import com.github.bugscatcher.dto.PostDTO;
import com.github.bugscatcher.dto.UserDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static com.github.bugscatcher.APIHelper.*;
import static com.github.bugscatcher.TestUtil.*;

@RunWith(Parameterized.class)
public class TestsPositive extends Abstract {

    private String property;

    public TestsPositive(String property) {
        this.property = property;
    }

    @Parameterized.Parameters(name = "with {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"username.one"}, {"username.multiple"}
        });
    }

    @Test
    public void checkEmailsFormat() {
        String username = properties.getProperty(property);
        Assert.assertNotNull(getMessageForNonExistentProperty(property), username);

        Arrays.stream(username.split(","))
                .forEach(uName -> {
                            UserDTO user = searchUser(uName);
                            Assert.assertNotNull(getMessageForNonExistentUser(uName), user);

                            PostDTO[] posts = getPosts(user.getId());
                            checkComments(posts);
                        }
                );
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
