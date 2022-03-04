package pl.gov.coi.pomocua.ads.accomodations;

import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.gov.coi.pomocua.ads.BaseOffer;
import pl.gov.coi.pomocua.ads.Location;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@EqualsAndHashCode(callSuper = true)
@Entity
class AccommodationOffer extends BaseOffer {

    @Embedded
    public Location location;

    @Min(1)
    public int guests;

    @Enumerated(STRING)
    public LengthOfStay lengthOfStay;

    @ElementCollection(targetClass = Language.class)
    @CollectionTable
    @Enumerated(STRING)
    public List<Language> hostLanguage;

    enum LengthOfStay {
        WEEK_1,
        WEEK_2,
        MONTH_1,
        MONTH_2,
        MONTH_3,
        LONGER
    }

    enum Language {
        UA, PL
    }
}

@Repository
interface AccommodationsRepository extends PagingAndSortingRepository<AccommodationOffer, Long> {

    Page<AccommodationOffer> findAllByLocation_VoivodeshipIgnoreCaseAndLocation_CityIgnoreCaseAndGuestsIsGreaterThanEqual(String voivodeship, String city, int guests, Pageable pageable);
    Page<AccommodationOffer> findAllByGuestsIsGreaterThanEqual(int guests, Pageable pageable);
}

