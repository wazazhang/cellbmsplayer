@echo sign the jar ...
@jarsigner -keystore waza.keystore -storepass 123456 loader.jar waza
@echo done
pause