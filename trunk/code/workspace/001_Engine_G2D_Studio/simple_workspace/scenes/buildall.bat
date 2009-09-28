
@FOR /D %%i IN (scene_*) DO @( 
@echo ###############################################################
@echo building... %%i
@copy .\scripts\build_script_set.bat %%i
@cd %%i
@call build_script_set.bat
@cd..
)

@call list_all.bat


