@CellGameEdit.exe scene.cpj ^
_script\output.properties ^
_script\scene_graph.script ^
_script\scene_jpg.script ^
_script\scene_png.script
@cd output
	@IF EXIST jpg.png @ (
		@del jpg.png
	)
	@IF EXIST png.png @ (
    	@del png.png
	)
    @cd set
		@IF EXIST jpg @(
			@cd jpg
			@del *.png
			@cd..
		)
		@IF EXIST png @(
			@cd png
			@del *.jpg
			@cd..
		)
    @cd..
@cd..