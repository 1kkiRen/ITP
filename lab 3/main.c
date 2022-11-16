#include <stdio.h>

void remove_dublicates(int *arr, int n){
    int temp[1000] = {0};
    int j = 0;
    for(int i = 0; i < n; i++){
        if (temp[arr[i]] == 0){
            temp[arr[i]] = 1;
            arr[j] = arr[i];
            j++;
        }
    }

    for(int i = 0; i < j; i++){
        printf("%d ", arr[i]);
    }
    
}

int main(){
    int n;
    int arr[n];
    
    scanf("%d", &n);

    for(int i = 0; i < n; i++){
        scanf("%d", &arr[i]);
    }

    remove_dublicates(arr, n);

    return 0;


}