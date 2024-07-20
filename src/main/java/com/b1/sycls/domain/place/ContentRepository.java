package com.b1.sycls.domain.place;

import com.b1.sycls.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Place, Long> {

}
