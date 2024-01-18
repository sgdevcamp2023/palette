package org.palette.easelsocialservice.service;

import org.junit.jupiter.api.Test;
import org.palette.easelsocialservice.persistence.HashtagRepository;
import org.palette.easelsocialservice.persistence.MediaRepository;
import org.palette.easelsocialservice.persistence.PaintRepository;
import org.palette.easelsocialservice.persistence.UserRepository;
import org.palette.easelsocialservice.persistence.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaintServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaintRepository paintRepository;
    @Autowired
    private HashtagRepository hashtagRepository;
    @Autowired
    private MediaRepository mediaRepository;

    @Test
    public void testAddPaints() {
        createSampleData();
        createLink();
        createHashtag();
        createMedia();
    }

    public  void createHashtag() {
        Paint paint = paintRepository.findByPid(1L).get();
        Paint paint2 = paintRepository.findByPid(2L).get();
        Hashtag tag = hashtagRepository.save(new Hashtag("hihi"));
        paint.addTag(tag, 1,3);
        paint2.addTag(tag, 3,4);

        paintRepository.save(paint);
        paintRepository.save(paint2);
    }

    public void createMedia() {
        Media media = new Media("/path/image", "image");
        mediaRepository.save(media);

        Paint paint = paintRepository.findByPid(1L).get();
        paint.addMedia(media);
        paintRepository.save(paint);
    }

    public void createLink() {
        Link link = new Link("shrotRrrr", "lodndndndd");
        Paint paint = paintRepository.findByPid(1L).get();
        paint.addLink(link);
        paintRepository.save(paint);

    }

    public void createSampleData() {
        User user1 = new User(1L, "user1", "nickname1", "path1");
        Paint paint1 = new Paint("content1");
        Paint paint2 = new Paint("content2");
        paint1.setAuthor(user1);
        paint2.setAuthor(user1);

        userRepository.save(user1);
        paintRepository.save(paint1);
        paintRepository.save(paint2);

        User user2 = new User(2L, "user2", "nickname2", "path2");
        Paint paint3 = new Paint("content3");
        Paint paint4 = new Paint("content4");
        paint3.setAuthor(user2);
        paint4.setAuthor(user2);

        userRepository.save(user2);
        paintRepository.save(paint3);
        paintRepository.save(paint4);
    }

}
