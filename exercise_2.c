#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>

/* The Alberti cipher is a polyalphabetic cipher system that uses two discs to encrypt and decrypt messages. One of the discs is stationary (the outer ring), and the other is movable (the inner ring). Let's look at an example of encoding the word "HELLOWORLD":

Outer ring (stationary):   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z
Inner ring (movable):       A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z

There is an initial shift of the inner ring, which rotates the disc anti-clockwise. For example, if the initial shift is 2 the inner ring would look like:

Outer ring (stationary):   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z
Inner ring (movable):       C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z   A   B

To encode the message, each character in the message found in the outer ring is replaced by the corresponding letter in the middle ring. For example, "HELLOWORLD" in the above would translate to "JGNNQYQTNF"

In addition to the initial shift, there is also a periodic shift that occurs every N characters, where N is known as the period length. For every N characters of the initial message, the inner ring is moved anti-clockwise by the periodic shift. Let's take a look at an example of encoding "HELLOWORLD" with an initial shift of 1, a periodic shift of 1, and a period length of 4.

The first four characters (HELL) are encoded with an initial shift of 1:
Outer ring (stationary):   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z
Inner ring (movable):       B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z   A

The second four characters (OWOR) are encoded with an additional shift of 1:
Outer ring (stationary):   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z
Inner ring (movable):       C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z   A   B

The final two characters (LD) are encoded with an additional shift of 1:
Outer ring (stationary):   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z
Inner ring (movable):       D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z   A   B   C

The resultant encoded message of HELLOWORLD is IFMMQYQTOG 

You must complete the encrypt_alberti(...) according to the above specification:

void encrypt_alberti(const char *message, int initial_shift, int periodic_shift, int period_length) - a function that takes in a message as a string and prints the message according to the Alberti Cipher with respective initial_shift, periodic_shift, and period_length.
Rules:

The outer ring will always be "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
The inner ring will always start as "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
You may assume message will contain only upper case alphabetic characters
Both initial and periodic shifts should be greater than or equal to zero
The period length has to be greater than zero. If the periodic shift is 0 then it doesn't matter what value the period length takes.
*/

/* Elements are: Message, Periodic Shift, Period Length. Therefore also need period*/
/* Need a "clock" to keep track of the periods: for loop covering the length of the message*/
/* Need a procedure to shift the letter, which takes as the input the Nth character, 
and shifts it by the current amount of the total shift
Use ASCII values of the letters to perform this. Be careful around the edges*/
/* A = 65 in ASCII; Z = 90 If the coded letter is greater than 90*/
/* Need a procedure to update the total shift every period length, but the periodic shift*/
/* Probably a good idea to keep the total shift bounded to avoid it getting too big: loop it around once it gets over 26*/
/* Start by building the encoder for a single letter with a fixed, static amount of shift*/
/* Then add the ability to vary the amount of shift*/
/* The add the ability to periodically vary this amount of shift*/
/* Test that individual letters are being shifted correctly */
/* Be esepcially careful to test A and Z, or letters that should convert to A or Z (or loop round)*/
/* Ensure we don't accept negative values for the intial and perdiodic shifts */


void encrypt_alberti(const char *message, int initial_shift, int periodic_shift, int period_length){
    if(initial_shift<0 || periodic_shift<0 || period_length <=0 || strlen(message)==0){
        return;
    }
    for (int i = 0; i < strlen(message); i++)
    {
        int periods = i/period_length;
        int offset = initial_shift + periodic_shift * periods;
        char uncodedLetter = message[i];
        int shift = uncodedLetter + offset;
        if(shift>90){
            shift=((shift - 90)%26)+64;
        }
        char codedLetter = shift;
        printf("%c", codedLetter);
    }
}

 int main (void){
    int initial_shift=260000001;
    int periodic_shift=260000000;
    int period_length=4;
    char message[10000]="AAAAAAAAAA";
    encrypt_alberti(message, initial_shift, periodic_shift, period_length);
} 