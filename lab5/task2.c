#include <stdio.h>

union packet{
    unsigned int num;
    struct{
        unsigned int version:4;
        unsigned int IHL:4;
        unsigned int DSCP:6;
        unsigned int ECN:2;
        unsigned int total_length:16;
    }bits;
};


int main(){
    union packet packet;
    scanf("%u", &packet.num);

    printf("%u %u %u %u %u", packet.bits.version, packet.bits.IHL, packet.bits.DSCP, packet.bits.ECN, packet.bits.total_length);

    return 0;
}