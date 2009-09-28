@del scene_list.txt

@FOR /D %%i IN (scene_*) DO @( 
@echo %%i >> scene_list.txt
@cd %%i\output
@findstr "\<World_[0-9]*[^_]\>" scene.properties >> ..\..\scene_list.txt
@cd ..\..
)
