#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>

/* 18 May 2007 is a Tuesday */

/* Calculate the number of 12ths of the Month that are Mondays between 1 Jan 1401 to 31 Dec 1800*/

/* Create a calendar logic including months and leap year maths */

/* check if the year is a leap year */


/* Step back from 18 May 2007 to 1 Jan 1401 assigning a dayName
    Count down days until it reaches 1, then reset days to be the number of days in the prior month
    Count down months until it's 1, then when days also reaches 1, set month to 12
    After month hits 1 and day hits one, decrement years by 1
    When it's a leap year, include a 29 February
    Once we reach 31 Dec 1800:
    If day = 12 and dayName = Monday then increment the counter by 1
    Until we hit 1 January 1401 */


/* Tests:
    Correctly counting down dates: days of month, months, years, days of week
    LeapYear is correctly identifying leap years
    Number of days in a month is calculating correctly
    Correctly identifying 12th Mondays
    Correctly only identifying 12th Mondays 
*/

bool isTheYearALeapYear(int year){
    if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
        return true;
    }
    else return false;
}

int numberOfDaysInMonth (int month, int year){
    int daysInMonth[12]={31,28,31,30,31,30,31,31,30,31,30,31};
    if (month ==2 && isTheYearALeapYear(year)){
        return 29;
   }
    else return daysInMonth[month-1];
}

int howManyDays (void) {
    struct date {
        int year;
        int month; //1-12
        int day; //1-31 (depending on month)
    };
    int weekdayNum = 7; // 7 = Tues, 6 = Mon ... 1 = Weds
    struct date test = {1980, 2, 26};
    struct date start = {2007, 5, 18};
    struct date startCount = {1800, 12, 31};
    struct date end = {1401, 1, 1};
    struct date check;
    int count = 0;
/* loop through years - decrement years by one after month = Jan & day = 1 */
    check = start;
    while(check.year>(end.year-1)){
        while (check.month>0){
            /* count if between counting dates, day is 12 and weekdayNum = 5 */
            while (check.day>0){

//                printf("The weekday is %d. ", weekdayNum);
                if ((check.year * 10000 + check.month*100 + check.day <= 
                startCount.year * 10000 + startCount.month*100 + startCount.day)
                && check.day == 12 
                && weekdayNum == 6)
                {
                count ++;
          //      printf("%d %d %d. \n",check.day, check.month, check.year);
          //      printf("The count is %d\n", count);            
                }
                check.day --;
                weekdayNum --;
                if (weekdayNum == 0){
                    weekdayNum = 7;
                }
            }
            if(check.month==1){
                check.day = numberOfDaysInMonth(12, check.year);}
            else check.day = numberOfDaysInMonth(check.month-1, check.year);
            check.month --;
        }
        check.month = 12;
        check.year --;
//      printf("%d is %sa leap year\n", check.year, isTheYearALeapYear(check.year) ? "":"not ");
    }
    int numberOfDays = numberOfDaysInMonth(test.month, test.year);
//    printf("Month %d in year %d has %d days in it\n", test.month, test.year, numberOfDays);
//    printf("The final count is %d\n", count);
    return(count);
}

int main(void)
{
    printf("The number of days is %d", howManyDays());
    return 0;
}

