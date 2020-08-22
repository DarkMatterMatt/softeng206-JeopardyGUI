REM set PATH_TO_FX_MODS="path\to\javafx-jmods-14"
jlink --module-path "%PATH_TO_FX_MODS%;out\production" --add-modules jeopardy --output out\jre
out\jre\bin\java -m jeopardy/se206.a2.Main
