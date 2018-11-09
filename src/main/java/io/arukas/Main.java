package io.arukas;

import java.util.Random;

/**
 * Created by IntelliJ IDEA. <br/>
 * http://www.voidcn.com/article/p-flsyeecf-ed.html
 *
 * @Auther: 牛玉贤 <br/>
 * @Date: 2018/11/1 11:34 <br/>
 * @Email: ncc0706@gmail.com <br/>
 */
public class Main {

    public static void main(String[] args) {
        testRandDivide();
    }

    public static void testRandDivide() {
        long t1 = System.currentTimeMillis();
        while (true) {
            int min = 1850;
            int max = 5200;

            int limit = 5000;

            int summary = 30300;
            // 创建一个用来存储结果数据的数组
            int[] k = new int[summary / limit];
            // 数组中有效数据长度
            int s = randDivide(min, max, summary, limit, k);
            if (s > 0) {
                break;
            }
        }
        System.out.println("time:" + (System.currentTimeMillis() - t1) + "ms.");
    }

    /**
     * 将一个整数summary随机拆分为N个在min~max之间的整数之和,并将分配结果存储到数组k中
     * 注意:min~max之间差越大,则越容易随机分配,反之,差越小,分配越困难,甚至无解
     * eg:min:5,max:8,summary:20,可以看出5
     * +5+5+5+5=20,6+6+8=20,7+7+6=20,5+7+8=20,但使用随机数5~8,可能
     * 第一次取到8,第2次也取到8,接下来无论怎么分配,也不可能为20
     */
    private static int randDivide(int min, int max, int summary, int limit, int[] k) {
        Random rand = new Random();
        int s = 0;
        int total = 0;
        for (int i = 0; i < summary / limit; i++) {
            // 产生一个在min与max之间的随机数
            int result = rand.nextInt(max - min + 1) + min;
            // 剩余数
            int remainder = summary - total;
            // 如果剩余数在min与max之间,则跳出
            if (remainder >= min && remainder <= max)
                break;
            if ((remainder - result) > 0) {
                k[i] = result;
                s = i;
                total += result;
            }
        }
        if ((summary - total) >= min && (summary - total) <= max) {
            s++;
            k[s] = (summary - total);
        }
        if ((summary - total) < min) {
            int r = (summary - total);
            // 使用随机数划分
            randDivideRemainder(max, k, s, r, 1);
        }

        // 校验
        int t = 0;
        for (int i = 0; i <= s; i++) {
            t += k[i];
        }
        if (t != summary) {
            s = 0;
        }

        int tot = 0;
        for (int i = 0; i < k.length; i++) {
            int result = k[i];
            System.out.println(result);
            tot += result;
        }
        System.out.println(tot);

        System.out.println("===========");

        System.out.println();
        return s;
    }

    /**
     * d:控制随机分配深度,防止无解导致栈溢出
     */
    private static void randDivideRemainder(int max, int[] k, int s, int r, int d) {
        Random rand = new Random();

        for (int i = 0; i <= s; i++) {
            int _max = (max - k[i]) < r ? (max - k[i]) : r;
            if (_max == 0)
                continue;
            int result = rand.nextInt(_max - 1 + 1) + 1;
            k[i] = k[i] + result;
            r = r - result;
            if (r == 0)
                break;
        }

        if (r > 0 && d <= 100) {
            randDivideRemainder(max, k, s, r, ++d);
        }
    }
}
