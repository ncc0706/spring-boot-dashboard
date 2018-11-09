package io.arukas.tools;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by IntelliJ IDEA. <br/>
 *
 * @Auther: 牛玉贤 <br/>
 * @Date: 2018/11/1 14:40 <br/>
 * @Email: ncc0706@gmail.com <br/>
 */
public class NumberRandDivideUtils {

    public static void main(String[] args) {

        Arrays.stream(randDivide(1000, 2200, 10300)).filter(x -> x > 0).forEach(System.out::println);
    }

    /**
     * 将一个整数summary随机拆分为N个在min~max之间的整数之和,并将分配结果存储到数组k中<br/>
     * 注意:min~max之间差越大,则越容易随机分配,反之,差越小,分配越困难,甚至无解 <br/>
     * eg:min:5,max:8,summary:20,可以看出5 <br/>
     * 5+5+5+5=20, 6+6+8=20, 7+7+6=20, 5+7+8=20,但使用随机数5~8,可能<br/>
     * 第一次取到8,第2次也取到8,接下来无论怎么分配,也不可能为20 int min = 5; int max = 6; int summary =20;<br/>
     * 缩减分配与叠加分配共用后完美解决 <br/>
     *
     * @param min     最小值
     * @param max     最大值
     * @param summary 待拆分的数字
     * @return
     */
    public static int[] randDivide(int min, int max, int summary) {
        int maxl = summary % min == 0 ? summary / min : summary / min + 1;
        int[] k = new int[maxl];
        // l：用来记录数组k中存储值的最后索引
        int l = 0;
        int total = 0;
        if (min == max) {// 如果两数相等，则常数
            if (summary % min == 0) {
                for (int i = 0; i < k.length; i++) {
                    k[i] = min;
                }
            }
        } else {
            Random rand = new Random();
            int remainder;
            for (int i = 0; i < k.length; i++) {
                // 剩余数
                remainder = summary - total;
                // 如果剩余数在min与max之间,则跳出去直接设置k[++l]为余数
                if (remainder >= min && remainder <= max) {
                    k[i] = remainder;
                    break;
                } else if (remainder < min) {
                    // 数组最小长度
                    int minl = summary % max == 0 ? summary / max : summary / max + 1;
                    // 如果i<最小数组长度,则缩减分配
                    if (i < minl) {
                        k[i] = remainder;
                        l = i;
                        randDivideLessenFiller(summary, min, k, l);
                        break;
                    } else {// 如果i>=最小数组长度，做叠加分配
                        randDivideRemainder(summary, max, k, l, remainder);
                        break;
                    }
                }
                // 产生一个在min与max之间的随机数
                int rd = rand.nextInt(max - min + 1) + min;
                k[i] = rd;
                total += rd;
                l = i;
            }
        }
        // 校验数组中的和不等于summary则返回0
        int t = 0;
        for (int i = 0; i < k.length; i++) {
            t += k[i];
        }
        if (t != summary) {
            return null;
        }
        return k;
    }

    private static void randDivideLessenFiller(int summary, int min, int[] k, int l) {
        if (summary / (l + 1) < min) return;
        Random rand = new Random();
        // 最小值与平均数之间的一个随机数
        int rv = rand.nextInt(summary / (l + 1) - min + 1) + min;
        int r = rv - k[l];
        int j = 0;
        while (r > 0 && j < 1000) {
            int i = rand.nextInt(l);
            if (k[i] - r >= min) {
                k[i] -= r;
                k[l] += r;
                break;
            }
            int _r = rand.nextInt(r) + 1;
            k[i] -= _r;
            k[l] += _r;
            r -= _r;
            j++;
        }
    }

    /**
     * l:数组最后的长度 r:余数 d:控制随机分配深度，j值一般达不到1000,防止无解导致栈溢出
     */
    private static void randDivideRemainder(int summary, int max, int[] k, int l, int r) {
        if (summary / (l + 1) > max) return;
        Random rand = new Random();
        int j = 0;
        while (r > 0 && j < 1000) {
            int i = rand.nextInt(l);
            if (k[i] + r <= max) {
                k[i] = k[i] + r;
                break;
            }
            int _tempMax = rand.nextInt(max - summary / (l + 1) + 1) + summary / (l + 1);
            for (int t = 0; t <= l; t++) {
                if (k[t] < _tempMax) {
                    // 最大数为程序要求的区间最大数-k[i]，即空间与余数作比较
                    int _max = (_tempMax - k[t]) < r ? (_tempMax - k[t]) : r;
                    if (_max == 0)
                        continue;
                    // 这里仍用随机数，而不是自动填补的原因是，不让数字整齐
                    int rn = rand.nextInt(_max) + 1;
                    k[t] += rn;
                    r -= rn;
                }
            }
            j++;
        }
    }


}
