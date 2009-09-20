@if exist waza.keystore (
@del waza.keystore
)

@echo creating keystore ...

@keytool ^
-genkey ^
-storepass "123456" ^
-keypass "123456" ^
-dname "CN=LordOnline, OU=LordOL, O=TilerGames, L=Shanghai, S=Shanghai, C=CN" ^
-alias waza ^
-keyalg RSA ^
-validity 1000 ^
-keystore waza.keystore

@echo done

@pause