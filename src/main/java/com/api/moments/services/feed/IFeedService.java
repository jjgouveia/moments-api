package com.api.moments.services.feed;

import com.api.moments.persistence.entities.Moment;

import java.util.List;

public interface IFeedService {
    List<Moment> getFeed(String userId);
}
