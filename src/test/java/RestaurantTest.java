import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.listeners.MockitoListener;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("all")
@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    @InjectMocks
    Restaurant restaurant;

    @Spy
    List<String> selectedMenuItems = new ArrayList<String>() ;

    @BeforeEach
    public void beforeEach(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        Restaurant spyRestaraunt = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("14:00:00")).when(spyRestaraunt).getCurrentTime();
        assertTrue(spyRestaraunt.isRestaurantOpen());


        //WRITE UNIT TEST CASE HERE
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        Restaurant spyRestaraunt = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("22:01:00")).when(spyRestaraunt).getCurrentTime();
        assertFalse(spyRestaraunt.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }


    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<COST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void when_no_menu_item_passed_the_total_order_cost_should_be_zero(){

        assertEquals(0, selectedMenuItems.size());
        assertEquals(0, restaurant.computeTotalOrderCost(selectedMenuItems));

    }

    @Test
    public void when_2_menu_item_passed_the_total_order_cost_should_be_388(){

        selectedMenuItems.add("Sweet corn soup");
        selectedMenuItems.add("Vegetable lasagne");

        assertEquals(2, selectedMenuItems.size());
        assertEquals(388, restaurant.computeTotalOrderCost(selectedMenuItems));

    }   


}