package com.api.moments.services.feed;

import com.api.moments.services.moment.response.MomentResponse;

import java.util.List;

public interface IFeedService {
    List<MomentResponse> getFeed(String userId);
}
