package com.example.bookmyshowbackend;

import com.example.bookmyshowbackend.models.*;
import com.example.bookmyshowbackend.repositories.*;
import com.example.bookmyshowbackend.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    private ShowSeatTypeRepository showSeatTypeRepository;


    private User testUser;
    private Show testShow;
    private List<ShowSeat> testShowSeats;

    @BeforeEach
    public void setup() {
        // Initialize the testShowSeats list
        testShowSeats = new ArrayList<>();

        // Create and save Region
        Region region = new Region();
        region.setName("New York");
        regionRepository.save(region);

        // Create and save Theatre
        Theatre theatre = new Theatre();
        theatre.setName("AMC Empire 25");
        theatre.setAddress("234 W 42nd St, New York, NY 10036");
        theatre.setRegion(region);
        theatreRepository.save(theatre);

        // Create and save SeatType
        SeatType seatType = new SeatType();
        seatType.setValue("Regular");
        seatTypeRepository.save(seatType);

        // Create and save Seats
        Seat seat1 = new Seat();
        seat1.setSeatNumber("A1");
        seat1.setRowVal(1);
        seat1.setColumnVal(1);
        seat1.setSeatType(seatType);
        seatRepository.save(seat1); // Save Seat 1

        Seat seat2 = new Seat();
        seat2.setSeatNumber("A2");
        seat2.setRowVal(1);
        seat2.setColumnVal(2);
        seat2.setSeatType(seatType);
        seatRepository.save(seat2); // Save Seat 2

        // Create and save Screen
        Screen screen = new Screen();
        screen.setName("Screen 1");
        screen.setSeats(Arrays.asList(seat1, seat2)); // Associate seats after saving
        screen.setTheatre(theatre);
        screen.setScreenFeatures(Arrays.asList(ScreenFeature.TWO_D));
        screenRepository.save(screen);

        // Create and save Movie
        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setDirector("Christopher Nolan");
        movie.setYear("2010");
        movie.setGenre("Sci-Fi");
        movie.setActors(Arrays.asList("Leonardo DiCaprio", "Joseph Gordon-Levitt"));
        movie.setLanguages(Arrays.asList(Language.ENGLISH, Language.HINDI));
        movieRepository.save(movie);

        // Create and save Show
        testShow = new Show();  // Initialize testShow here
        testShow.setMovie(movie);
        testShow.setStartTime(new Date());
        testShow.setDuration(120);
        testShow.setScreen(screen); // Associate screen after saving
        showRepository.save(testShow);  // Save testShow

        // Create and save ShowSeats
        ShowSeat showSeat1 = new ShowSeat();
        showSeat1.setShow(testShow);  // Use testShow here
        showSeat1.setSeat(seat1); // Associate seat after saving
        showSeat1.setShowSeatStatus(ShowSeatStatus.AVAILABLE);
        showSeatRepository.save(showSeat1);

        ShowSeat showSeat2 = new ShowSeat();
        showSeat2.setShow(testShow);  // Use testShow here
        showSeat2.setSeat(seat2);
        showSeat2.setShowSeatStatus(ShowSeatStatus.AVAILABLE);
        showSeatRepository.save(showSeat2);

        // Add show seats to the testShowSeats list
        testShowSeats.add(showSeat1);
        testShowSeats.add(showSeat2);

        // Create and save User
        testUser = new User();  // Initialize testUser here
        testUser.setEmail("john.doe@example.com");
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setPassword("password123");
        userRepository.save(testUser);  // Save testUser

        // Create and save ShowSeatType (for pricing)
        ShowSeatType showSeatType = new ShowSeatType();
        showSeatType.setSeatType(seatType);  // Associate seat type
        showSeatType.setShow(testShow);      // Associate show
        showSeatType.setPrice(250);        // Set the price for this seat type
        showSeatTypeRepository.save(showSeatType);  // Save show seat type with price
    }

    // Test case for successful booking
    @Test
    public void testBookTicket_Success() {
        List<Integer> showSeatIds = Arrays.asList(testShowSeats.get(0).getId(), testShowSeats.get(1).getId());
        Booking booking = bookingService.bookTicket(showSeatIds, testShow.getId(), testUser.getId());

        assertNotNull(booking);
        assertNotNull(booking.getShowSeats());
    }

    // Test case for seat not available
    @Test
    public void testBookTicket_SeatNotAvailable() {
        // Block a seat to simulate unavailable seat
        testShowSeats.get(0).setShowSeatStatus(ShowSeatStatus.BOOKED);
        showSeatRepository.save(testShowSeats.get(0));

        List<Integer> showSeatIds = Arrays.asList(testShowSeats.get(0).getId(), testShowSeats.get(1).getId());
        assertThrows(RuntimeException.class, () -> {
            bookingService.bookTicket(showSeatIds, testShow.getId(), testUser.getId());
        });
    }
}
