class Solution {
    public int majorityElement(int[] nums) {
        int max = 0;
        int count = 0;
        for(int num : nums){
            if(count ==0){
                max = num;
            }
            if(num == max){
                count++;
            }
            else{
                count--;
            }
        }
        return max;
    }
}