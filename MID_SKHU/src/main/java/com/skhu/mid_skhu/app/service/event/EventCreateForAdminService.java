package com.skhu.mid_skhu.app.service.event;

import com.skhu.mid_skhu.app.dto.event.requestDto.EventCreateRequestDto;
import com.skhu.mid_skhu.app.dto.event.responseDto.EventCreateResponseDto;
import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import com.skhu.mid_skhu.app.repository.EventRepository;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import com.skhu.mid_skhu.global.util.GetS3Resource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EventCreateForAdminService {

    private final EventRepository eventRepository;
    private final S3ImageFileService s3ImageFileService;

    @Transactional
    public ApiResponseTemplate<EventCreateResponseDto> createEvent(EventCreateRequestDto requestDto, Principal principal) {

        List<InterestCategory> category = convertInterestCategory(requestDto.getInterestCategoryList());

        if (category.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_CATEGORY_IN_INTEREST_EXCEPTION,
                    ErrorCode.NOT_FOUND_CATEGORY_IN_INTEREST_EXCEPTION.getMessage() + "\n" + category);
        }

        List<GetS3Resource> imagesUrl = uploadImages(requestDto.getImages(), "event");

        Event event = createEventEntity(requestDto, category, imagesUrl.stream()
                .map(GetS3Resource::getImageUrl)
                .collect(Collectors.toList()));
        eventRepository.save(event);

        EventCreateResponseDto responseDto = createEventRegistrationResponseDto(event);

        return ApiResponseTemplate.<EventCreateResponseDto>builder()
                .status(201)
                .success(true)
                .message("이벤트 등록 성공")
                .data(responseDto)
                .build();
    }

    private Event createEventEntity(EventCreateRequestDto requestDto,
                                    List<InterestCategory> category, List<String> imageUrls) {

        return Event.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .eventLocation(requestDto.getEventLocation())
                .startAt(requestDto.getStartAt())
                .endAt(requestDto.getEndAt())
                .categories(category)
                .imageUrls(imageUrls)
                .build();
    }

    private List<InterestCategory> convertInterestCategory(List<String> interestCategoryList) {
        return interestCategoryList.stream()
                .map(String::toUpperCase)
                .map(InterestCategory::convertToCategory)
                .collect(Collectors.toList());
    }

    private List<GetS3Resource> uploadImages(List<MultipartFile> files, String directory) {
        return s3ImageFileService.uploadImageFiles(files, directory);
    }

    private EventCreateResponseDto createEventRegistrationResponseDto(Event event) {
        return EventCreateResponseDto.builder()
                .title(event.getTitle())
                .description(event.getDescription())
                .eventLocation(event.getEventLocation())
                .startAt(event.getStartAt())
                .endAt(event.getEndAt())
                .interestCategoryList(event.getCategories().stream()
                        .map(InterestCategory::name)
                        .collect(Collectors.toList()))
                .imageUrls(event.getImageUrls())
                .build();
    }
}
