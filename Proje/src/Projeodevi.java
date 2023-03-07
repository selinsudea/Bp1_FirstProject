/**
 * @file Proje
 * @description Kullanıcıdan bir karmaşık metin alıp bunu sözlükteki metinler
 * ile kıyaslayan ve bunları düzeltip yazdıran bir program.Aynı zamanda
 * sözlükteki kelimenin karmaşık metinde kaç kez geçtiğini de yazdırır.
 * @assignment Proje 1
 * @date 29.11.2021 - 12.12.2021
 * @author Selin Sude ATALI / selinsude.atali@stu.fsm.edu.tr
 */
import java.util.Scanner;

public class Projeodevi {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Karmaşık metninizi giriniz:");
        String karmasikMetin = input.nextLine();
        System.out.print("Sözlük Giriniz:");
        String sozluk = input.nextLine();

        String[] bozukMetin = diziyecevirme(karmasikMetin);
        String[] sozluK = diziyecevirme(sozluk);
        String duzeltilmisMetin = metinDuzeltme(bozukMetin, sozluK);
        String[] diziduzeltilenMetin = diziyecevirme(duzeltilmisMetin);
        for (int i = 0; i < diziduzeltilenMetin.length; i++) {
            System.out.print(diziduzeltilenMetin[i] + " ");
        }
        int[] kackezgecti = kackezgecti(diziduzeltilenMetin, sozluK);
        System.out.println("");
        for (int i = 0; i < kackezgecti.length; i++) {
            System.out.print(kackezgecti[i] + " ");
        }

    }

    /*
    Diğer metotlarımda da kullanmak için bir metot hazırladım. Burada boşluk sayısından yararlandım
    ama en son kelimeyi saydırabilmek için return'de +1 değer döndürmesini istedim bu sayede metnimin
    uzunluğunu doğru bir şekilde bulabilecektim.
     */
    public static int metinuzunlugu(String metin) {
        int metinuzunlugu = 0;
        for (int i = 0; i < metin.length(); i++) {
            if (metin.charAt(i) == ' ') {
                metinuzunlugu++;
            }
        }
        return metinuzunlugu + 1;
    }

    /*
    Projede kendi ignorecase metodumu oluşturmam gerekiyordu. Bunun için derste de öğrendiğimiz bu metotu hazırladım.
    Bu metot büyük harfli girilen karakterleri küçük harfli haline çeviriyor. Bunun için ASCII tablodan yararlandım.
     */
    public static char kucukharfecevirme(char metin) {
        int fark = 32;
        for (int i = 0; i < metin; i++) {
            if (65 <= metin && metin <= 90) {
                metin = (char) (metin + fark);
            } else {
                metin = (char) (metin);
            }
        }
        return metin;
    }

    /*
    Bu metot sayesinde karmaşık metnimi ve sözlüğü bir diziye çevirdim.Boşluk görene kadar "birlestirme" adında
    string türünde bir değişkenimde kelimeyi birleştirmesini ve boşluk görünce onu
    ilgili indexe yazdırıp bu değişkeni sıfırlayıp indexin değerini bir arttırmasını istedim.
     */
    public static String[] diziyecevirme(String metin) {
        String[] diziyecevirme = new String[metinuzunlugu(metin)];
        int dizielemani = 0;
        String birlestirme = "";
        for (int i = 0; i < metin.length(); i++) {
            if (metin.charAt(i) != ' ') {
                birlestirme += metin.charAt(i);
            } else {
                diziyecevirme[dizielemani] = birlestirme;
                dizielemani++;
                birlestirme = "";
            }
        }
        diziyecevirme[dizielemani] = birlestirme;
        return diziyecevirme;
    }

    /*
    Metottaki kelimemi harflere ayırıp diziye atıyorum.
     */
    public static char[] karaktercevirme(String sozcuk) {
        char[] karakter = new char[sozcuk.length()];
        for (int i = 0; i < sozcuk.length(); i++) {
            karakter[i] = sozcuk.charAt(i);
        }
        return karakter;
    }

    /*
    Bu metodu karmaşık metin ile sözlükteki kelimem eşit olana kadar sağa kaydırması için yaptım.
     */
    public static String rotateRight(String word) {
        char[] karakter = karaktercevirme(word);
        String yenimetin = "";
        char temp = karakter[0];
        for (int i = 1; i < karakter.length; i++) {
            yenimetin += karakter[i];
        }
        yenimetin += temp;
        return yenimetin;
    }

    /*
    Bu metodum karmaşık metnim ve sözlük metindeki kelimem aynı mı diye kontrol ediyor.Bu metotta iki kelimemin uzunluğu 
    eşit ise for döngüsü içine girmesini istedim çünkü bu kodum için daha kısa ve kolay sonuç veriyordu.Küçük büyük 
    harfe duyarlılık olmadan kontrol ediyor.
     */
    public static boolean Kelimemi(String karmasikMetin, String sozluk) {

        boolean ayni = false;
        char[] karmasikmetin = karaktercevirme(karmasikMetin);
        char[] sozlukkarakter = karaktercevirme(sozluk);
        if (karmasikmetin.length == sozlukkarakter.length) {
            for (int i = 0; i < karmasikmetin.length; i++) {
                if (karmasikmetin[i] == sozlukkarakter[i] || kucukharfecevirme(karmasikmetin[i]) == sozlukkarakter[i]) {
                    ayni = true;
                } else {
                    ayni = false;
                    break;
                }

            }
        }
        return ayni;
    }

    /*
    Burada ki metotumda diğer metotlarımdan da yararlandım ve karmaşık metnimdeki kelimem sözlükteki kelimeme eşit olana kadar
    kontrol edip sağa kaydırıyorum. Eşit ise veya eşit olduğunda "duzeltilmismetin" e atıyorum. Bu şekilde çıktım da doğru sonucu
    alabileceğim. Sondaki if koşulu açmamın nedeni kelimeler arası boşluk oluşturmaktır. 
     */
    public static String metinDuzeltme(String[] karmasikMetin, String[] sozlukMetin) {
        String duzeltilmismetin = "";
        
        for (int i = 0; i < karmasikMetin.length; i++) {
            String temp = karmasikMetin[i];
            for (int j = 0; j < sozlukMetin.length; j++) {
                int sayac;
                for (sayac = 0; sayac < temp.length(); sayac++) {
                    if (Kelimemi(temp, sozlukMetin[j])) {
                        break;
                    } else {
                        temp = rotateRight(temp);
                    }
                }
                if (temp.equals(sozlukMetin[j])) {
                    break;
                }
            }
            duzeltilmismetin += temp;
            if (i < karmasikMetin.length - 1) {
                duzeltilmismetin += " ";
            }
        }
        return duzeltilmismetin;
    }

    /*
    En son metnimin düzgün halinin sözlükteki kelimelere bakarak kaç kez geçtiğini kontrol etmek için bir metot yazdım.
    Eğer sözlükteki metin düzgün metnimde geçmemişse "0" olarak yazdırıyorum.
     */
    public static int[] kackezgecti(String[] duzgunmetin, String[] sozlukmetin) {
        int[] kackezgecti = new int[sozlukmetin.length];
        int sayac = 0;
        for (int i = 0; i < sozlukmetin.length; i++) {
            for (int j = 0; j < duzgunmetin.length; j++) {
                if (Kelimemi(duzgunmetin[j], sozlukmetin[i])) {
                    sayac++;
                }
            }
            kackezgecti[i] = sayac;
            sayac = 0;
        }
        return kackezgecti;
    }
}