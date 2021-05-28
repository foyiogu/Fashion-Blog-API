package com.webblog.service.ServiceImpl;

import com.webblog.POJO.FavouriteMapper;
import com.webblog.model.Favourite;
import com.webblog.model.Person;
import com.webblog.model.Post;
import com.webblog.repository.FavouriteRepository;
import com.webblog.repository.PersonRepository;
import com.webblog.repository.PostRepository;
import com.webblog.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {
    @Autowired
    FavouriteRepository favouriteRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    PostRepository postRepository;

    public List<FavouriteMapper> getFavouritesByUser( Person person){
        List<FavouriteMapper> favourites = new ArrayList<>();

        try {
            Person user = personRepository.findPersonByEmail(person.getEmail()).get();
            List<Favourite> favouritesData = favouriteRepository.findFavouriteByCurrentUserId(user.getId());

            favouritesData.forEach(favourite -> {

                FavouriteMapper favouriteMapper = new FavouriteMapper();

                Post post = postRepository.findById(favourite.getPostId()).get();

                favouriteMapper.setPostId(favourite.getPostId());
                favouriteMapper.setPostBody(post.getBody());
                favouriteMapper.setPostTitle(post.getTitle());

                favourites.add(favouriteMapper);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  favourites;
    }

    public boolean saveFavourite(Person person, Long postId){

        boolean flag = false;

        Person person1 = personRepository.findPersonByEmail(person.getEmail()).get();

        List<Favourite> favourites = favouriteRepository.findFavouriteByCurrentUserIdAndPostId(person1.getId(), postId);

        try {

            if(favourites.size() == 0){
                Favourite favourite = new Favourite();
                favourite.setCurrentUserId(person1.getId());
                favourite.setPostId(postId);

                favouriteRepository.save(favourite);

            }

            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

}
