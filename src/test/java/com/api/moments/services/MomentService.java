package com.api.moments.services;

import com.api.moments.models.MomentModel;
import java.util.List;

public interface MomentService {
  MomentModel create(MomentModel momentModel);
  List<MomentModel> getAll();
}
