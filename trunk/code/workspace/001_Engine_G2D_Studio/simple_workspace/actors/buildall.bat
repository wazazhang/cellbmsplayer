
@FOR /D %%i IN (actor_*) DO @( 
@echo ###############################################################
@echo building actor %%i
@copy .\scripts\build_script_set.bat %%i
@cd %%i
@call build_script_set.bat
@cd..
)

@call list_all.bat
