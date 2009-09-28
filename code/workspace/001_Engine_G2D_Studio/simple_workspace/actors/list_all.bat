@del actor_list.txt

@FOR /D %%i IN (actor_*) DO @( 
@echo %%i >> actor_list.txt
@cd %%i\output
@findstr "\<Sprite_[0-9]*[^_]\>" actor.properties >> ..\..\actor_list.txt
@cd ..\..
)
