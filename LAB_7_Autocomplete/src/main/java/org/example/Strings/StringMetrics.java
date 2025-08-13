package org.example.Strings;

/// Класс реализующий поиск максимальной общей последовательности

public class StringMetrics
{
    public static int longestCommonSubstringLength(String str1, String str2)
    {
        // Если одна из строк пустая возвращаем 0
        if(str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty())
        {
            return 0;
        }

        //  Если 2 строка больше 1, то меняем их местами, и меняем их длины
        int length_string_1 = str1.length();
        int length_string_2 = str2.length();

        if(length_string_2 > length_string_1)
        {
            String strTemp = str1;
            str1 = str2;
            str2 = strTemp;

            int intTemp = length_string_1;
            length_string_1 = length_string_2;
            length_string_2 = intTemp;
        }

        // Создаем массив размером длина второй строки + 1
        int[] lcsLastRow = new int[length_string_2 + 1];
        char[] str_1_arr = str1.toLowerCase().toCharArray();
        char[] str_2_arr = str2.toLowerCase().toCharArray();

        int c00 = 0;
        int c01 = 0;

        // assert str1 > str2
        for(int i = 0; i < length_string_1; i++)
        {
            c00 = 0;
            for(int j = 0; j < length_string_2; j++)
            {
                c01 = lcsLastRow[j + 1];

                lcsLastRow[j  + 1] = (str_1_arr[i] == str_2_arr[j]) ?
                        (c00 + 1) :
                        Math.max(c01, lcsLastRow[j]);

                c00 = c01;
            }
        }

        return lcsLastRow[length_string_2];
    }
}
