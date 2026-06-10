package com.example.arnonfinalhta;

import java.util.ArrayList;
import java.util.Collections;

public class TriviaQuestionBank {

    public static ArrayList<TriviaQuestion> getQuestions() {

        ArrayList<TriviaQuestion> questions = new ArrayList<>();

        questions.add(new TriviaQuestion("באיזה צבע מזוהה הפועל תל אביב?",
                new String[]{"אדום", "צהוב", "ירוק"}, 0));

        questions.add(new TriviaQuestion("איזה צבע נוסף מזוהה עם הפועל תל אביב?",
                new String[]{"לבן", "כחול", "כתום"}, 0));

        questions.add(new TriviaQuestion("באיזו עיר משחקת הפועל תל אביב?",
                new String[]{"תל אביב", "חיפה", "ירושלים"}, 0));

        questions.add(new TriviaQuestion("באיזה אצטדיון מזוהה הפועל תל אביב?",
                new String[]{"בלומפילד", "טדי", "סמי עופר"}, 0));

        questions.add(new TriviaQuestion("מי היריבה העירונית הגדולה של הפועל תל אביב?",
                new String[]{"מכבי תל אביב", "מכבי חיפה", "הפועל באר שבע"}, 0));

        questions.add(new TriviaQuestion("איך נקרא משחק בין הפועל תל אביב למכבי תל אביב?",
                new String[]{"דרבי תל אביבי", "קלאסיקו", "גמר גביע"}, 0));

        questions.add(new TriviaQuestion("איזה כינוי נפוץ לאוהדי הפועל תל אביב?",
                new String[]{"אדומים", "ירוקים", "צהובים"}, 0));

        questions.add(new TriviaQuestion("כמה נקודות מקבלים על ניצחון בכדורגל?",
                new String[]{"3", "2", "1"}, 0));

        questions.add(new TriviaQuestion("כמה נקודות מקבלים על תיקו בכדורגל?",
                new String[]{"1", "2", "0"}, 0));

        questions.add(new TriviaQuestion("כמה נקודות מקבלים על הפסד בכדורגל?",
                new String[]{"0", "1", "3"}, 0));

        questions.add(new TriviaQuestion("כמה שחקנים פותחים בהרכב של קבוצת כדורגל?",
                new String[]{"11", "10", "12"}, 0));

        questions.add(new TriviaQuestion("מה תפקיד השוער?",
                new String[]{"למנוע שערים", "להבקיע בלבד", "לשפוט"}, 0));

        questions.add(new TriviaQuestion("מה מטרת המשחק בכדורגל?",
                new String[]{"להבקיע יותר שערים מהיריבה", "להחזיק בכדור בלבד", "לקבל יותר קרנות"}, 0));

        questions.add(new TriviaQuestion("מהי בעיטת עונשין מ-11 מטר?",
                new String[]{"פנדל", "קרן", "חוץ"}, 0));

        questions.add(new TriviaQuestion("מה קורה כאשר הכדור יוצא מקו הצד?",
                new String[]{"הוצאת חוץ", "פנדל", "שער"}, 0));

        questions.add(new TriviaQuestion("מה קורה כאשר הכדור יוצא מקו השער אחרי נגיעה של שחקן הגנה?",
                new String[]{"קרן", "חוץ", "פנדל"}, 0));

        questions.add(new TriviaQuestion("מה מסמן כרטיס אדום?",
                new String[]{"הרחקה", "אזהרה בלבד", "שער"}, 0));

        questions.add(new TriviaQuestion("מה מסמן כרטיס צהוב?",
                new String[]{"אזהרה", "הרחקה מיידית תמיד", "חילוף"}, 0));

        questions.add(new TriviaQuestion("כמה מחציות יש במשחק כדורגל רגיל?",
                new String[]{"2", "3", "4"}, 0));

        questions.add(new TriviaQuestion("כמה דקות יש במחצית רגילה בכדורגל?",
                new String[]{"45", "30", "60"}, 0));

        questions.add(new TriviaQuestion("מה משמעות המילה 'נבדל'?",
                new String[]{"עמדה לא חוקית בהתקפה", "שער עצמי", "פציעה"}, 0));

        questions.add(new TriviaQuestion("מהו חילוף בכדורגל?",
                new String[]{"החלפת שחקן בשחקן אחר", "שינוי צבע חולצה", "החלפת שופט"}, 0));

        questions.add(new TriviaQuestion("איזה תפקיד משחק בדרך כלל קרוב לשער היריבה?",
                new String[]{"חלוץ", "שוער", "בלם"}, 0));

        questions.add(new TriviaQuestion("איזה תפקיד משחק בדרך כלל בהגנה?",
                new String[]{"בלם", "חלוץ", "שופט"}, 0));

        questions.add(new TriviaQuestion("מה תפקיד הקשר?",
                new String[]{"לחבר בין ההגנה להתקפה", "לעמוד בשער", "לשפוט"}, 0));

        questions.add(new TriviaQuestion("איזו מילה מתארת אוהד שתומך בקבוצה?",
                new String[]{"אוהד", "שופט", "יריב"}, 0));

        questions.add(new TriviaQuestion("מהי טבלת ליגה?",
                new String[]{"דירוג קבוצות לפי תוצאות", "רשימת שופטים", "רשימת אצטדיונים"}, 0));

        questions.add(new TriviaQuestion("מה מציין הפרש שערים?",
                new String[]{"שערי זכות פחות שערי חובה", "מספר אוהדים", "מספר כרטיסים"}, 0));

        questions.add(new TriviaQuestion("מהי כתבה באפליקציה?",
                new String[]{"פריט חדשות", "שאלה בטריוויה", "סיסמה"}, 0));

        questions.add(new TriviaQuestion("מה מציגה טבלת הדירוג באפליקציה?",
                new String[]{"ניקוד משתמשים", "מחירי כרטיסים", "מזג אוויר"}, 0));

        questions.add(new TriviaQuestion("כמה נקודות מקבל המשתמש על תשובה נכונה בטריוויה?",
                new String[]{"10", "5", "20"}, 0));

        questions.add(new TriviaQuestion("כמה תשובות אפשריות יש לכל שאלה בטריוויה?",
                new String[]{"3", "2", "4"}, 0));

        questions.add(new TriviaQuestion("כמה זמן יש למענה על שאלה בטריוויה?",
                new String[]{"30 שניות", "10 שניות", "60 שניות"}, 0));

        questions.add(new TriviaQuestion("מה קורה כאשר הזמן נגמר בטריוויה?",
                new String[]{"עוברים לשאלה הבאה", "האפליקציה נסגרת", "הניקוד מוכפל"}, 0));

        questions.add(new TriviaQuestion("איפה נשמר ניקוד הטריוויה?",
                new String[]{"Firebase Realtime Database", "גלריה", "מצלמה"}, 0));

        questions.add(new TriviaQuestion("מהו Firebase Authentication?",
                new String[]{"מערכת התחברות והרשמה", "עורך תמונות", "נגן מוזיקה"}, 0));

        questions.add(new TriviaQuestion("מהו Firestore באפליקציה?",
                new String[]{"מסד נתונים לפרטי משתמש", "כפתור", "צליל"}, 0));

        questions.add(new TriviaQuestion("מהו Realtime Database באפליקציה?",
                new String[]{"מסד נתונים לניקוד בזמן אמת", "מסך חדשות", "תפריט צבעים"}, 0));

        questions.add(new TriviaQuestion("מה עושה כפתור פרופיל?",
                new String[]{"מציג פרטי משתמש", "מוחק את האפליקציה", "פותח מצלמה"}, 0));

        questions.add(new TriviaQuestion("מה עושה כפתור חדשות?",
                new String[]{"מציג כתבות", "מתחיל משחק", "משנה סיסמה"}, 0));

        questions.add(new TriviaQuestion("מה עושה כפתור טבלה?",
                new String[]{"מציג טבלת ליגה", "שולח מייל", "מכבה מוזיקה"}, 0));

        questions.add(new TriviaQuestion("מה עושה כפתור טריוויה?",
                new String[]{"פותח משחק שאלות", "פותח מפה", "פותח גלריה"}, 0));

        questions.add(new TriviaQuestion("מה עושה כפתור טבלת דירוג?",
                new String[]{"מציג ניקוד משתמשים", "פותח חדשות", "מוחק משתמש"}, 0));

        questions.add(new TriviaQuestion("מהי מטרת מסך הבית?",
                new String[]{"להציג משחקים ומידע מרכזי", "לאפס סיסמה", "להציג קוד"}, 0));

        questions.add(new TriviaQuestion("מהי מטרת מסך פרטי משחק?",
                new String[]{"להציג מידע על משחק שנבחר", "להתחבר לחשבון", "להשמיע מוזיקה בלבד"}, 0));

        questions.add(new TriviaQuestion("מהי מטרת מסך ההרשמה?",
                new String[]{"יצירת משתמש חדש", "הצגת טבלת ליגה", "משחק טריוויה"}, 0));

        questions.add(new TriviaQuestion("מהי מטרת מסך ההתחברות?",
                new String[]{"כניסה לחשבון קיים", "הצגת חדשות", "הצגת ניקוד בלבד"}, 0));

        questions.add(new TriviaQuestion("מהי מטרת מסך שחזור סיסמה?",
                new String[]{"שליחת מייל איפוס סיסמה", "הצגת משחקים", "שינוי צבעים"}, 0));

        questions.add(new TriviaQuestion("איזה רכיב מציג רשימות באפליקציה?",
                new String[]{"RecyclerView", "Toast", "Intent"}, 0));

        questions.add(new TriviaQuestion("איזה רכיב משמש למעבר בין מסכים?",
                new String[]{"Intent", "ImageView", "ProgressBar"}, 0));

        questions.add(new TriviaQuestion("איזה רכיב מציג הודעה קצרה למשתמש?",
                new String[]{"Toast", "Spinner", "RecyclerView"}, 0));

        questions.add(new TriviaQuestion("איזה רכיב מציג התקדמות זמן בטריוויה?",
                new String[]{"ProgressBar", "EditText", "ImageView"}, 0));

        questions.add(new TriviaQuestion("איזה רכיב משמש לבחירת מגדר בהרשמה?",
                new String[]{"Spinner", "RecyclerView", "MediaPlayer"}, 0));

        questions.add(new TriviaQuestion("איזה רכיב משמש לבחירת תאריך לידה?",
                new String[]{"DatePickerDialog", "Toast", "Volley"}, 0));

        questions.add(new TriviaQuestion("איזו ספרייה משמשת לטעינת חדשות מהאינטרנט?",
                new String[]{"Volley", "Glide", "FirebaseAuth"}, 0));

        questions.add(new TriviaQuestion("איזו ספרייה משמשת לטעינת תמונות חדשות?",
                new String[]{"Glide", "Volley", "CountDownTimer"}, 0));

        questions.add(new TriviaQuestion("מה עושה MusicManager?",
                new String[]{"מנהל מוזיקת רקע", "שומר סיסמאות", "מציג טבלה"}, 0));

        questions.add(new TriviaQuestion("מה עושה BaseActivity?",
                new String[]{"מרכז ניווט ומוזיקה", "מייצג קבוצה", "מייצג כתבה"}, 0));

        questions.add(new TriviaQuestion("מהו Adapter באנדרואיד?",
                new String[]{"מחבר בין נתונים לתצוגה", "מסד נתונים", "שירות התחברות"}, 0));

        questions.add(new TriviaQuestion("מה מייצגת המחלקה Game?",
                new String[]{"משחק כדורגל", "משתמש", "כתבה"}, 0));

        questions.add(new TriviaQuestion("מה מייצגת המחלקה Team?",
                new String[]{"קבוצה בטבלה", "שאלה", "מוזיקה"}, 0));

        questions.add(new TriviaQuestion("מה מייצגת המחלקה User?",
                new String[]{"משתמש וניקוד", "אצטדיון", "חדשה"}, 0));

        questions.add(new TriviaQuestion("מה מייצגת המחלקה NewsItem?",
                new String[]{"כתבת חדשות", "משחק", "כפתור"}, 0));

        questions.add(new TriviaQuestion("מה מייצגת המחלקה TriviaQuestion?",
                new String[]{"שאלת טריוויה", "שחקן", "אוהד"}, 0));

        questions.add(new TriviaQuestion("מהי פעולה של saveScore?",
                new String[]{"שמירת ניקוד", "טעינת תמונה", "פתיחת חדשות"}, 0));

        questions.add(new TriviaQuestion("מהי פעולה של loadLeaderboard?",
                new String[]{"טעינת טבלת דירוג", "התחברות", "בחירת תאריך"}, 0));

        questions.add(new TriviaQuestion("מהי פעולה של loadNews?",
                new String[]{"טעינת חדשות מהאינטרנט", "שמירת ניקוד", "השמעת צליל"}, 0));

        questions.add(new TriviaQuestion("מהי פעולה של checkAnswer?",
                new String[]{"בדיקת תשובת המשתמש", "יצירת משתמש", "פתיחת פרופיל"}, 0));

        questions.add(new TriviaQuestion("מהי פעולה של startTimer?",
                new String[]{"הפעלת טיימר", "טעינת חדשות", "מחיקת משתמש"}, 0));

        questions.add(new TriviaQuestion("מהי פעולה של logoutUser?",
                new String[]{"התנתקות מהחשבון", "פתיחת כתבה", "שמירת משחק"}, 0));

        questions.add(new TriviaQuestion("מה קורה כאשר תשובה נכונה?",
                new String[]{"הניקוד עולה", "האפליקציה נסגרת", "הסיסמה משתנה"}, 0));

        questions.add(new TriviaQuestion("מה קורה כאשר תשובה שגויה?",
                new String[]{"מוצג משוב מתאים", "נמחק החשבון", "נפתח דפדפן"}, 0));

        questions.add(new TriviaQuestion("מה קורה כאשר לוחצים על כתבה?",
                new String[]{"הכתבה נפתחת בדפדפן", "נוסף ניקוד", "מתחיל טיימר"}, 0));

        questions.add(new TriviaQuestion("מה קורה כאשר לוחצים על משחק?",
                new String[]{"נפתח מסך פרטי משחק", "נפתח מסך הרשמה", "נמחק המשחק"}, 0));

        questions.add(new TriviaQuestion("מה קורה כאשר לוחצים על התנתקות?",
                new String[]{"נפתח אישור יציאה", "נוסף ניקוד", "נפתחת כתבה"}, 0));

        questions.add(new TriviaQuestion("מהו UID ב־Firebase?",
                new String[]{"מזהה ייחודי למשתמש", "צבע כפתור", "שם אצטדיון"}, 0));

        questions.add(new TriviaQuestion("למה משתמשים ב־addValueEventListener?",
                new String[]{"לקבלת עדכונים בזמן אמת", "להשמעת מוזיקה", "לבחירת תמונה"}, 0));

        questions.add(new TriviaQuestion("למה משתמשים ב־Collections.sort?",
                new String[]{"למיון טבלת הדירוג", "לשליחת מייל", "להפעלת צליל"}, 0));

        questions.add(new TriviaQuestion("מה משמעות TBD במשחקים?",
                new String[]{"טרם נקבע", "ניצחון", "הפסד"}, 0));

        questions.add(new TriviaQuestion("מה מוצג במסך טבלת הליגה?",
                new String[]{"קבוצות, נקודות והפרש שערים", "רק כתבות", "רק משתמשים"}, 0));

        questions.add(new TriviaQuestion("מה מוצג במסך חדשות?",
                new String[]{"כתבות כדורגל", "שאלות טריוויה", "פרטי חשבון"}, 0));

        questions.add(new TriviaQuestion("מה מוצג במסך פרופיל?",
                new String[]{"פרטי משתמש", "טבלת ליגה", "כתבות"}, 0));

        questions.add(new TriviaQuestion("מה מוצג במסך טבלת דירוג?",
                new String[]{"משתמשים וניקוד", "משחקים בלבד", "חדשות בלבד"}, 0));

        questions.add(new TriviaQuestion("מהי חוויית משתמש טובה?",
                new String[]{"שימוש נוח וברור", "מסך ריק", "טעינה בלי סוף"}, 0));

        questions.add(new TriviaQuestion("מה היתרון של Firebase?",
                new String[]{"שמירת נתונים בענן", "שינוי צבע בלבד", "הצגת תמונה בלבד"}, 0));

        questions.add(new TriviaQuestion("מה היתרון של API חיצוני?",
                new String[]{"קבלת מידע עדכני", "מחיקת נתונים", "כיבוי מוזיקה"}, 0));

        questions.add(new TriviaQuestion("מה היתרון של טבלת דירוג?",
                new String[]{"יצירת תחרות בין משתמשים", "הסתרת ניקוד", "מחיקת שאלות"}, 0));

        questions.add(new TriviaQuestion("מה היתרון של טיימר בטריוויה?",
                new String[]{"הוספת אתגר למשחק", "ביטול ניקוד", "פתיחת חדשות"}, 0));

        Collections.shuffle(questions);

        return questions;
    }
}