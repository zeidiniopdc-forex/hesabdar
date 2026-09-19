# حسابدار (Hesabdar)

اپلیکیشن حسابداری شخصی فارسی برای اندروید.

## امکانات (نسخه فعلی)

- مدیریت حساب‌ها (نقد، بانک، کارت، کیف پول)
- دسته‌بندی درآمد و هزینه
- ثبت تراکنش (درآمد / هزینه / انتقال) با به‌روزرسانی خودکار موجودی
- بودجه ماهانه
- تعهدات (بدهی، وام، قسط، طلب)
- دارایی‌ها (نقد، طلا، ارز، کریپتو و ...)
- داشبورد اولیه با موجودی کل و آخرین تراکنش‌ها
- seed اولیه: حساب «نقد» + دسته‌بندی‌های پیش‌فرض فارسی

## تکنولوژی

- Kotlin
- Jetpack Compose + Material 3
- Room (SQLite)
- DataStore
- Navigation Compose
- minSdk 26 / targetSdk 34

## ساختار پروژه

```
app/src/main/java/com/hesabdar/app/
├── HesabdarApplication.kt
├── MainActivity.kt
├── data/
│   ├── Entities.kt
│   ├── HesabdarDatabase.kt
│   ├── dao/          # Account, Category, Transaction, Budget, Obligation, Asset
│   └── repository/
└── ui/
    ├── DashboardViewModel.kt
    └── theme/Theme.kt
```

## اجرا

1. پروژه را در Android Studio باز کنید.
2. Gradle Sync کنید.
3. یک امولاتور یا دستگاه با API 26+ انتخاب کنید.
4. Run.

یا با خط فرمان (نیاز به Gradle Wrapper):

```bash
./gradlew assembleDebug
```

## واحد پول

همه مبالغ به **ریال** (Long) ذخیره می‌شوند. نمایش تومان فقط در لایه UI انجام شود.

## لایسنس

MIT (پیشنهادی)
