# -*- coding: utf-8 -*

from sys import exit, platform
from os import system, listdir, remove
from os.path import isdir, join
from shutil import rmtree

from argparse import ArgumentParser

###########################################################################
################### CAN BE DIRECTLY ADAPTED HERE###########################
default_unix_fx_path = r'/opt/JavaFX/javafx-sdk-20/lib'
default_win_fx_path  = r'C:\Users\ryzwa\Downloads\UnlockGame-main\javafx-sdk-20\lib'
default_project_root_path      = r'.'
default_project_root_classpath = r'.'
# default_project_main_class     = 'Launcher'
default_serial_directory       = 'SerializationFiles'
default_java_version=20
###########################################################################

fx_jar = ['.media', '.fxml', '.web', '.swing', '.graphics', '-swt', '.base', '.controls']

def manageArguments():
    parser = ArgumentParser()
    parser.add_argument("command",                  help="choose between C(ompile), D(ocumentation), LS(Launch Swing interface), LF(Launch Fx interface), or K (for cleaning)", choices=['C','D', 'LS', 'LF','K'])
##########################################################################
#################### TO ADAPT DURING RUNTIME #############################
    parser.add_argument("--fx_path",                help=f"Path to FX lib directory, the current default value is [{default_unix_fx_path}] on unix OS and [{default_win_fx_path}] on Windows OS.")
    parser.add_argument("--project_root_path",      help=f"path to the root directory containing Launcher.java; the current default value is [{default_project_root_path}].")
    parser.add_argument("--project_root_classpath", help=f"classpath to the root directory containing Launcher.java (with package naming i.e with \".\"); the current default value is [{default_project_root_classpath}].")
    parser.add_argument("--serial_directory",       help=f"the directory where the serial file will be created; the current default value is [{default_serial_directory}].")
##########################################################################
    return parser.parse_args()

def searchForJarInDir(directory,elements=list()):
    if not isdir(directory):
        print(f'{directory} is not a valid directory ==> execution aborted')
        exit(200)
        
    for library in listdir(directory):
            print(f"{library} has been included in found JAR list")
            elements.append(join(directory,library))
    
    return elements

def makeClassPathTarget(elements, unix_mode):
    if len(elements)==0:
        print(f"Bad configuration of the classpath because not element has been given ==> execution aborted")
        exit(100)

    target=""
        
    if not unix:        
        target+="\""
    
    for element in elements:

        target+=element
        
        if unix_mode:
            target+=':'
        else:
            target+=';'
            
    target=target[:-1]
    
    if not unix_mode:
        target+="\""

    return target
        
if __name__ == "__main__":
    
    args=manageArguments()
    command=args.command

    if args.serial_directory:      
        default_serial_directory=args.serial_directory

    if not isdir(default_serial_directory):
        print(f'{default_serial_directory} is not a valid directory')
        exit(10)

    if args.project_root_path:
        default_project_root_path=args.project_root_path

    if args.project_root_classpath:
        default_project_root_classpath=args.project_root_classpath
    
    if command=='K':
        print(f'\nCleaning {default_serial_directory}')
        print(f"\tDeleting the content of directory '{default_serial_directory}'")
        for filename in listdir(default_serial_directory):
            path=join(default_serial_directory,filename)
            if isdir(path):
                rmtree(path)
            else:
                remove(path)
        exit(0)
        
    if command=='C':
        print(f'\nTry to compile the project on {platform} operating system\n')
    elif command == 'LF':
        print(f'\nTry to run FX version of the project on {platform} operating system\n')
    elif command == 'LS':
        print(f'\nTry to run SWING version of the project on {platform} operating system\n')
    else:
        print(f'\nTry to generate the documentation of the projet\n')
        
    unix=True
    if platform.startswith('win'):
        print("It seems to be some sort of windows system I guess")
        unix=False
        
        if args.fx_path:
          default_win_fx_path=args.fx_path

        default_fx_path=default_win_fx_path

    else:
        print("It seems to be some sort of unix system I guess")

        if args.fx_path:
          default_unix_fx_path=args.fx_path
        default_fx_path=default_unix_fx_path

    if not isdir(default_fx_path):
        print(f'{default_fx_path} is not a valid directory')
        exit(20)

    files = listdir(default_fx_path)
    missing_jar = []
    
    for jar in fx_jar:
        jar_name = 'javafx'+jar+'.jar'

        if not jar_name in files:
            missing_jar.append(jar_name)

    if not len(missing_jar)==0:
        print('Some jar file are missing :')
        for jar in missing_jar:
            print(jar)
        exit(30)
            
    print(f'\tJava FX lib directory  = {default_fx_path}')
    print(f'\tProject root path      = {default_project_root_path}')
    print(f'\tProject root classpath = {default_project_root_classpath}')
    # print(f'\tProject main           = {default_project_main_class}')
    print()

    # elements=['cls']
    if unix==False:
        elements2=searchForJarInDir(default_win_fx_path)
    else:
        elements2=searchForJarInDir(default_unix_fx_path)
    # classpath=makeClassPathTarget(elements+elements2,unix)
    
    if command=='C':    
        for jar in fx_jar:
            target =f'javac --module-path {default_fx_path} --add-modules javafx.controls,javafx.fxml,javafx.media *.java'

    elif command == 'LF':
        target=f'java --module-path {default_fx_path} --add-modules javafx.controls,javafx.fxml,javafx.media Launch_FX.java'
    elif command == 'LS':
        target=f'java --module-path {default_fx_path} --add-modules javafx.controls,javafx.fxml,javafx.media Launch_SWING.java'

    print(f'\nTarget command is :\n{target}\n')
    system(target)
    
    exit(0)
