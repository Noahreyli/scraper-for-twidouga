from json import load
import re,os,pickle,datetime
from time import sleep
from selenium import webdriver
from selenium.webdriver.chrome.options import Options



dir = os.getcwd()
os.chdir(os.path.dirname(os.path.abspath(__file__)))


def writeBinary(object,path):
    with open("binary","rb") as f:
        binary = pickle.load(f)
    
    with open(path,"wb") as f:
        pickle.dump(object+set(binary),f)

def loadBinary():
    with open("binary","rb") as f:
        return pickle.load(f)

def preserveToday():
    if not os.path.exists("archive/"+str(dt.month)):
        os.makedirs("archive/"+str(dt.month))
    f = open("archive/"+str(dt.month)+"/"+str(dt.day)+".txt","wb")
    today = loadBinary()

    for i in today:
        f.write(i.encode("utf-8"))
        f.write("\n".encode("utf-8"))
    os.remove("binary")


extension_path = "C:\\Users\\aokit\\Desktop\\scraper-for-twidouga\\src\\profile\\Profile1\\Extensions\\bgnkhhnnamicmpeenaelnjfhikgbkllg\\4.1.1_0"
options = Options()
options.add_argument("--user-data-dir=C:\\Users\\aokit\\Desktop\\scraper-for-twidouga\\src\\profile")
options.add_argument("--profile-directory=Profile1")
options.add_argument("--lang=jp")
options.add_argument(f"load-extension={extension_path}")
exePath = "C:\\Users\\aokit\\Desktop\\scraper-for-twidouga\\src\\chromedriver.exe"
driver = webdriver.Chrome(executable_path=exePath, options=options)

url = "https://www.twidouga.net/realtime_t.php"
driver.get(url)

for i in range(5):
    sleep(1)
    driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
#表示完了

contents = driver.find_elements_by_class_name("item_w180")
urls = set()
for i in contents:
    urls.add((re.sub("\?tag=\d{1,2}", "", i.find_element_by_tag_name("a").get_attribute("href")),re.sub("\?tag=\d{1,2}", "", i.find_element_by_tag_name("img").get_attribute("src"))))


driver.quit()

dt = datetime.datetime.now()

urls = list(urls - set(loadBinary()))
#更新なし
if len(urls) == 0:
    print("No new videos")
    exit()
#更新あり
for i in urls:
    print(i)
if(dt.hour == 23):
    preserveToday()

os.chdir(dir)
exit()
