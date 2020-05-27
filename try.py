
from tkinter import *
from tkinter import filedialog
import sys
#!/usr/bin/env python
# coding: utf-8

# In[1]:


class Base:
    def __init__(self,x,y):
        self.x = x
        self.y = y
    def getter(self):
        return self.x, self.y
    def setter(self,x,y):
        self.x = x
        self.y = y
    def mean(self,b1,b2):
        self.x = self.x + abs(b1.x - b2.x)
        self.y = self.y + abs(b1.y - b2.y)
    @staticmethod
    def equals(b1,b2):
        if(b1.x == b2.x and b1.y == b2.y):
            return True
        
        return False
    
    @staticmethod
    def add(b1,b2):
        b1x , b1y = b1.getter()
        b2x,b2y = b2.getter()
        b = Base(0,0)
        b.setter(b1x+b2x, b1y+b2y)
        return b
    
    @staticmethod
    def sub(b1,b2):
        b1x , b1y = b1.getter()
        b2x,b2y = b2.getter()
        b = Base(0,0)
        b.setter(b1x-b2x, b1y-b2y)
        return b
        
    def copy(self):
        return Base(self.x,self.y)
    @staticmethod
    def minimize(b1,b2):
        return Base(min(b1.x,b2.x),min(b1.y,b2.y))
    @staticmethod
    def maximize(b1,b2):
        return Base(max(b1.x,b2.x),max(b1.y,b2.y))
    @staticmethod
    def mod_minimize(b1,b2,b3):
        return Base(min(b1.x,b2.x,b3.x),min(b1.y,b2.y,b3.y))
    @staticmethod
    def mod_maximize(b1,b2,b3):
        return Base(max(b1.x,b2.x,b3.x),max(b1.y,b2.y,b3.y))
    
    @staticmethod
    def convert(str):
        ls = str.split(",")
        b = Base(float(ls[0]),float(ls[1]))
        return b
    


# In[2]:


class Line:
    def __init__(self):
        pass
        
    def int1(self,beg,ter,sh1,sh2):
        self.val1 = Base.minimize(beg,ter)
        self.val2 = Base.maximize(beg,ter)
        self.beg = beg
        self.ter = ter
        self.sh1 = sh1
        self.sh2 = sh2
        
    def int2(self,beg,ter):
        self.beg = beg
        self.ter = ter
        self.val1 = Base.minimize(beg,ter)
        self.val2 = Base.maximize(beg,ter)
    def ycorr(self,val):
        d = (self.term.y-self.beg.y)/(self.term.x-self.beg.x)
        ans = (self.beg.y + d*(val - self.beg.x))
        if(ans<self.val1.y):
            ans = self.val1.y
        if(ans>self.val2.y):
            ans = self.val2.y
        return ans
    
    def xcorr(self,val):
        d = (self.term.x-self.beg.x)/(self.term.y-self.beg.y)
        ans = (self.beg.x + d*(val - self.beg.y))
        if(ans<self.val1.x):
            ans = self.val1.x
        if(ans>self.val2.x):
            ans = self.val2.x
        return ans
    def copy(self):
        l = Line()
        l.beg = self.beg.copy()
        l.ter = self.ter.copy()
        l.val1 = self.val1.copy()
        l.val2 = self.val2.copy()
        
        return l
    @staticmethod
    def lister(lines):
        l = list()
        for i in lines:
            l.append(i.copy())
        return l
    @staticmethod
    def curv(l):
        d1,d2 = l.ter.getter()
        v = list()
        for i in range(0.05,1,0.05):
            a1 = (i**3)*l.beg.x+3*i*i*(1-i)*sh1.x + 3*i*(1-i)*(1-i)*sh2.x + (i-1)*(i-1)*(i-1)*l.terr.x
            a2 = (i**3)*l.beg.y+3*i*i*(1-i)*sh1.y + 3*i*(1-i)*(1-i)*sh2.y + (i-1)*(i-1)*(i-1)*l.terr.y
            z = Line()
            z.int2(Base(d1,d2),Base(a1,a2))
            v.append(z)
            d1 = a1
            d2 = a2
        return v
    @staticmethod
    def fin_curv(l):
        v = list()
        for i in l:
            if(i.sh1!=null and i.sh2!=null):
                v = v + curv(i)
            else:
                v.append(l)
        return v


# In[3]:


class Form:
    def __init__(self):
        self.l = list()
    def f_min(self):
        a = self.l[0].val1.x
        b = self.l[0].val1.y
        for i in l:
            if(i.val1.x<a):
                a = i.min.x
            if(i.val1.y<b):
                b = i.min.y
        self.min = Base(a,b)

    def f_max(self):
        a = self.l[0].val2.x
        b = self.l[0].val2.y
        for i in l:
            if(i.val2.x>a):
                a = i.min.x
            if(i.val2.y>b):
                b = i.min.y
        self.max = Base(a,b)
    
    def int1(self,l):
        self.l = l
        if(len(self.l)>0):
            self.f_min()
            self.f_max()
    def add(self,ls):
        if(ls!=null):
            self.l.append(ls)
            self.f_min()
            self.f_max()
    def bma(self,c,a1,a2):
        if(c == 'i'):
            if(self.max.x > a1 and a1!=-1):
                self.max.x = a1
            if(self.max.y > a2 and a2!=-1):
                self.max.y = a2
        else:
            if(self.max.x < a1 and a1!=-1):
                self.max.x = a1
            if(self.max.y < a2 and a2!=-1):
                self.max.y = a2
    def bmi(self,c,a1,a2):
        if(c == 'i'):
            if(self.min.x > a1 and a1!=-1):
                self.min.x = a1
            if(self.min.y > a2 and a2!=-1):
                self.min.y = a2
        else:
            if(self.min.x < a1 and a1!=-1):
                self.min.x = a1
            if(self.min.y < a2 and a2!=-1):
                self.min.y = a2
            
    def def_val(self,b):
        k = 0
        if(b.x<self.min.x):
            k = k + 8
        elif(b.x > self.max.x):
            k = k + 4
        if(b.y < self.min.y):
            k = k + 1
        elif(b.y > self.max.y):
            k = k + 2
        return k
    def coll(self,a1,b1,a2,b2):
        if(a1.x > b2.x or b1.x < a2.y or a1.y > b2.y or b1.y < a2.y):
            return True
        return False
    def copy(self):
        f = Form()
        f.l = Line.lister(self.l)
        f.min = self.min.copy()
        f.max = self.max.copy()
        
        return f
    @staticmethod
    def lister(ls):
        l = list()
        for i in ls:
            l.append(i.copy())
        return l
    def tr(self):
        l = list()
        mx = self.min.x + (self.max.x - self.min.x)/2
        my = self.min.y + (self.max.y - self.min.y)/2
        for i in self.l:
            if(coll(i.min,i.max,Base(mx,self.min.y),Base(self.max.x,my))):
                l.append(i)
                
        if(len(l)>0):
            return Form(l)
        return null
    def tl(self):
        l = list()
        mx = self.min.x + (self.max.x - self.min.x)/2
        my = self.min.y + (self.max.y - self.min.y)/2
        for i in self.l:
            if(coll(i.min,i.max,self.min,Base(mx,my))):
                l.append(i)
                
        if(len(l)>0):
            return Form(l)
        return null
    def br(self):
        l = list()
        mx = self.min.x + (self.max.x - self.min.x)/2
        my = self.min.y + (self.max.y - self.min.y)/2
        for i in self.l:
            if(coll(i.min,i.max,Base(mx,my),Base(self.max.x,self.max.y))):
                l.append(i)
                
        if(len(l)>0):
            return Form(l)
        return null
    def bl(self):
        l = list()
        mx = self.min.x + (self.max.x - self.min.x)/2
        my = self.min.y + (self.max.y - self.min.y)/2
        for i in self.l:
            if(coll(i.min,i.max,Base(self.min.x,my),Base(mx,self.max.y))):
                l.append(i)
                
        if(len(l)>0):
            return Form(l)
        return null
    def cohenSutherland(self):
        tmi=null,tma=null
        rm = list()
        for i in range(len(self.l)):
            b1=self.l[i].beg.copy()
            b2=self.l[i].beg.copy()
            c1=self.def_val(b1)
            c2=self.def_val(b2)
            while((c1|c2)!=0 and (c1&c2)==0):
                if (c1==0):
                    b1,b2 = b2,b1
                    c1,c2 = c2,c1
                
                if ((c1&1)==1):
                    b1.x=self.l[i].xcorr(self.min.y)
                    b1.y=self.min.y
                
                elif ((c1&2)==2):
                    b1.x=self.l[i].xcorr(self.max.y)
                    b1.y=self.max.y
                
                elif ((c1&4)==4):
                    b1.x=self.max.x
                    b1.y=self.l[i].ycorr(self.max.x)
                
                else:
                    b1.x=self.min.x
                    b1.y=self.l[i].ycorr(self.min.x)
                
            c1=self.def_val(b1)
            
            if(c1!=0 or c2!=0):
                rm.append(i)
            else:
                tmi=Base.mod_minimize(tmi,b1,b2)
                tma=Base.mod_maximize(tma, b1, b2)
            
        j = 0
        for i in rm:
            self.l.pop((i-j))
            j=j+1
        
        if (tmi!=null and tma!=null):
            self.min.x=tmi.x
            self.min.y=tmi.y
            self.max.x=tma.x
            self.max.y=tma.y
        
        
        if(len(self.l)>0):
            return True
        else:
            return False
    
    def subd(self,b):
        r = list()
        if(self.max.x - self.min.x < b.x and self.max.y - self.min.y < b.y ):
            r.append(self) #bk
            return r
        mx=self.min.x+(self.max.x-self.min.x)/2.0
        my=self.min.y+(self.max.y-self.min.y)/2.0
        
        f = tl()
        if (f!=null):
            f.bmi('s',self.min.x,self.min.y)
            f.bma('i',mx,my)
            if(f.cohenSutherland()):
                l.append(f)
        f = tr()
        if (f!=null):
            f.bmi('s',mx,self.min.y)
            f.bma('i',self.max.x,my)
            if(f.cohenSutherland()):
                r.append(f)
        f = bl()
        if (f!=null):
            f.bmi('s',self.min.x,my)
            f.bma('i',mx,self.max.y)
            if(f.cohenSutherland()):
                r.add(f)
        f = br()
        if (f!=null):
            f.bmi('s',mx,my)
            f.bma('i',self.max.x,self.max.y)
            if(f.cohenSutherland()):
                r.add(r)
        return r
        


# In[4]:


class CONTAINER:
    def __init__(self):
        self.que = 0
        self.size = 0
        
        self.lines = list()
        
    
    def cons1(self,l):
        self.form = list()
        self.lines = Line.lister(l)
        self.form.append(Form(self.lines))
        self.min = self.form[0].min.copy()
        self.min = self.form[0].min.copy()
        self.position = Base((-1)*form[0].min.x,(-1)*form[0].min.y)
        
    def trial(self):
        temp = list()
        m = Base(0,0)
        for i in form:
            m.mean(m.min,m.max)
        m.x = m.x/len(form)
        m.y = m.y/len(form)
        for i in form:
            temp = temp + subd(m)
        form = temp;
        setTop();
        setBot();
        
        
    def copy(self):
        c = CONTAINER()
        c.lines = Line.lister(self.lines)
        c.form = Form.lister(self.form)
        c.max = self.max
        c.min = self.min
        return c
    
    def inn(self,ls,i,ln):
        temp = list()
        for j in range(len(ls)):
            if(j==i):
                temp.append(ln)
            temp.append(ls[j])
        return temp
    
    def pp(self,a,b):
        self.position.setter((-1)*self.min.x+a,(-1)*self.min.y+b)
    
    def top(self):
        i = 0
        temp = list()
        for f in form:
            flag = True
            for i in range(len(temp)):
                if(f.min.x<temp[i].max.x and f.max.x > temp[i].min.x):
                    if(f.min.y > temp[i].min.y):
                        flag = False
            if(flag==True):
                if(i == len(temp)):
                    l = Line()
                    l.int2(f.min,Base(f.max.x,f.min.y))
                    temp.append(l)
                else:
                    self.inn(temp, i, f)
        self.top = temp
        
    def bot(self):
            i = 0
            temp = list()
            for f in form:
                flag = True
                for i in range(len(temp)):
                    if(f.min.x<temp[i].max.x and f.max.x > temp[i].min.x):
                        if(f.max.y < temp[i].max.y):
                            flag = False
                if(flag==True):
                    if(i == len(temp)):
                        l = Line()
                        l.int2(Base(f.min.x,f.max.y),f.max)
                        temp.append(l)
                    else:
                        self.inn(temp, i, f)
            self.bot = temp
                    


# In[ ]:

class Helper:
    def __init__(self,str):
        self.str = str
        self.vc = list()
        self.check = False;
        self.w = list()
        
    
    def create(self,cp):
        self.check = True
        cp_P = list()
        c_p = ""
        for i in range(len(cp)):
            if('>' in cp[i]):
                cp.pop(i)
                self.check = False
            elif('"' in cp[i] ):
                c_p = cp[i][:(cp[i].find('"')+1)]
                cp[i] = c_p;
            elif('e' in cp[i]):
                c_p = cp[i]
                cp_P = c_p.split(",")
                if('e' in cp_P[0]):
                    cp_P[0]="0"
                else:
                    cp_P[1]="0"
                c_p = cp_P[0]+","+cp_P[1]
                cp[i] = c_p
        return cp
    
    def process(self,cp, fl,tr):
        tmp = Base(0,0)
        l = list()
        av = False;
        Or = Base(0,0)
        an = Base(0,0)
        l2  = Line()
        l2.int1(an,an,an,an)
        no = Base(0,0)
        tr1 = Base(0,0)
        tr2 = Base(0,0)
        Ord = Base.add(tr,Base.convert(cp[0]))
        for i in range(len(cp)):
            if("m" in cp[i] or "l" in cp[i]):
                av = True
                fl = 1
                if("m" in cp[i]):
                    Or = Base.convert(cp[i+1])
            elif("c" in cp[i]):
                av = True
                fl = 2
            elif("M" in cp[i] or "L" in cp[i]):
                av = True
                fl = 3
                if("M" in cp[i]):
                    tmp = Base.add(Base.convert(cp[i+1]),tr)
                    Or = Base.sub(tmp,Or)
            elif("C" in cp[i]):
                fl = 4
                av = True
            elif("z" in cp[i]):
                av = True
                fl = 5
            if(av == True):
                i = i+1
                av = False
            if(fl == 1):
                no = Base.add(an,Base.convert(cp[i]))
                l2 = Line()
                l2.int1(an,no,null,null)
                l.append(l2)
                an= no
            elif(fl == 2):
                tr1 = Base.add(an,Base.convert(cp[i]))
                i = i+1
                tr2 = Base.add(an,Base.convert(cp[i]))
                i = i + 1
                no = Base.add(an,Base.convert(cp[i]))
                l2 = Line()
                l2.int1(an,no,tr1,tr2)
                l.append(l2)
                an= no
            elif(fl == 3):
                tmp = Base.add(tr,Base.convert(cp[i]))
                no = Base.sub(tmp,Ord)
                l2 = Line()
                l2.int1(an,no,an,no)
                l.append(l2)
                an= no
            elif(fl == 4):
                tmp = Base.add(tr,Base.convert(cp[i]))
                tr1 = Base.sub(tmp,Ord)
                i = i + 1
                
                tmp = Base.add(tr,Base.convert(cp[i]))
                tr2 = Base.sub(tmp,Ord)
                i = i + 1
                
                tmp = Base.add(tr,Base.convert(cp[i]))
                no = Base.sub(tmp,Ord)
                
                
                l2 = Line()
                l2.int1(an,no,tr1,tr2)
                l.append(l2)
                an= no
                
            elif(fl == 5):
                if(i<len(cp) and (not "m" in cp[i])):
                    l2 = Line()
                    l2.int1(an,Or,an,Or)
                    l.append(l2)
                    an = Or
                else:
                    i = i - 1
                    
                if(i == len(cp)-1):
                    l2 = Line()
                    l2.inst1(an,Or,null,null)
                    l.append(l2)
            elif(fl == 6):
                no = Base.add(an,Base.convert(cp[i]))
                an = no
        c = CONTAINER()
        c.cons1(l)
        
        self.vc.append(c)
        
    
    def selfRead(self):
        tr =  Base(0,0)
        d =   Base(0,0)
        trG = Base(0,0)
        trL = Base(0,0)
        Or =  Base(0,0)
        cp = list()
        fl = 1
        al = 1
        r = True
        size = 0
        
        fo = open(self.str,"r")
        while(1):
            rdd = fo.read(1)
            if(not rdd):
                break
            if(rdd < '<' and fo.read(1) == 'g'):
                while(1):
                    rdd = fo.read(1)
                    if(not rdd):
                        break
                    al = al - 1
                    if(rdd=='t'):
                        w = list(fo.read(5))
                        if(w[0]=='r' and w[1]=='a' and w[2]=='n' and w[3]=='s' and w[4]=='l'):
                            line = fo.readline()
                            buf=list(line.split("/"));
                            buf=list(buf[1].split("/"));
                            trG = Base.convert(buf[0])
                    if(rdd == 'g' and fo.read(1)=='>'):
                        fo.close()
                    if(rdd=='<'):
                        al = 2
                    if(al == 1 and rdd == 'g'):
                        r = True
                        cp.clear()
                        size = 0
                        fr = Base(0,0)
                        while(1):
                            rdd = fo.read(1)
                            if(rdd=='g' and fo.read(1)=='>'):
                                self.process(cp,fl,trG)
                                flag = 1
                                break
                            if(rdd=='t'):
                                w=list(fo.read(5))
                                if(w[0]=='=' and w[1]=='a' and w[2]=='n' and w[3]=='s' and w[4]=='l'):
                                    line = fo.readline()
                                    buf=list(line.split("/"));
                                    buf=list(buf[1].split("/"));
                                    trG = Base.convert(buf[0])
                            if(rdd=='d'):
                                w = list(fo.read(4))
                                if(w[0]=='=' and w[1]=='"' and (w[2]=='m' or w[2]=='M')and w[3]==' '):
                                    if(w[2]=='M'):
                                        flag=3
                                    line = fo.readline()
                                    cp_P = line.split(" ")
                                    cp= cp + cp_P
                                    cp= create(cp)
                                    d = Base.convert(cp[size])
                                    ts = Base.sub(d,fr)
                                    fr = Base.convert(cp[size])
                                    if(size!=0):
                                        cp.insert(size,"m")
                                        size=size+1
                                    size+=len(cp_P)
                        if(al==1 and rdd=='p'):
                            r = True
                            trL = Base(0,0)
                            fo.seek(8000,2)
                            while((fo.read(1))!='>'):
                                fo.seek(1,2)
                                rdd=fo.read(1)
                                if(rdd == 't'):
                                    w = list(fo,read(5))
                                    if(w[0]=='r' and w[1]=='a' and w[2]=='n' and w[3]=='s' and w[4]=='l'):
                                        line = fo.readline()
                                        buf = list(line.split("/"))
                                        buf = list(buf[1].split("/"))
                                        trL = Base.convert(buf[0])
                            fo.seek(0,0)
                            tr = Base.add(trG,trL)
                            while((fo.read(1)) != '>' and r):
                                fo.seek(1,2)
                                rdd = fo.read(1)
                                if(rdd=='d'):
                                    w=list(fo,read(5))
                                    if(w[0]=='=' and w[1]=='"' and (w[2]=='m' or w[2]=='M') and w[3]==' '):
                                        if(w[2]=='M'):
                                            fl = 3
                                        cp.clear()
                                        line = fo.readline()
                                        splt = line.split(" ")
                                        cp = cp + splt
                                        cp = create(cp,fl,tr)
                                        fl = 1


# In[33]:


class Painter:
    def __init__(self):
        self.s = list()
        self.sp = False
        self.sr = False
        
    def cons(self,v):
        self.s = v
        self.sp = False
        self.sr = False
        regulate()
    def regulate(self):
        ind ,reg = -1, 0
        nv = list()
        for i in self.s:
            i.que = -1
            i.size = (s.max.x - s.min.x)*(s.max.y-s.min.y)
        for i in range(self.s.size):
            temp = -1
            for j in range(self.s.size):
                if(self.s[j].que ==-1 and temp<self.s[j].size ):
                    temp = self.s[j].size
                    ind = j
            reg+=1
            self.s[ind].que = reg
            nv.append(self.s[ind])
        self.s = nv
    def prepare(self,str,boo):
        if(str==""):
            pass
        h = Helper(str)
        h.selfRead()
        if(boo==True):
            self.s += self.s +  h.vc
        else:
            self.s = h.vc
        regulate()
        for i in self.s:
            for j in range(3):
                i.trial()
        t_swap()
        #draw()
    
    def t_swap(self):
        vc = list()
        for i in self.s:
            swap(vc,i)
    def swap(self,vc,sh):
        ad_x,ad_y = 99999999,0
        h = (3543.0 - (sh.max.y-sh.min.y))/1400.0
        
        for i in range(1400):
            tx = 0
            ty = h*i
            for a in vc:
                if((a.position.y+a.min.y)<(h*i+sh.max.y-sh.min.y) and 
                   (a.position.y+a.max.y)>(i*h)):
                    
                    if(tx<(a.position.x+a.max.x)):
                        tx = a.position.x + a.max.x
            if(tx<ad_x):
                ad_x = tx
                ad_y = ty
        sh.pp(ad_x,ad_y)
        vc.append(sh)
    
    def Visualize(self):
        otherFrame = Tk()
        otherFrame.geometry("1000x1000")
        otherFrame.title("Cutter")
        canvas_width = 300
        canvas_height = 400
        w = Canvas(otherFrame, width=canvas_width,height=canvas_height)
        w.pack()
        for i in s:
            if(self.sp==True):
                w.create_oval(int(i.position.x/5),int(i.position.y/5),5,5,fill="#0000ff")
                w.create_oval(int((i.position.x+i.max.x)/5),int((i.position.y+i.max.y)/5),5,5,fill="#000000")
                w.create_oval(int((i.position.x+i.min.x)/5),int((i.position.y+i.min.y)/5),5,5,fill="#ff3d00")
            for j in i.lines:
                w.create_line(int((i.position.x + j.beg.x)/5),int((i.position.y + j.beg.y)/5),int((i.position.x+l.ter.x)/5),int((i.position.y+l.ter.y)/5))
            
            if(self.sr==True):
                w.create_rectangle(int((i.position.x+i.min.x)/5), int((i.position.y+i.min.y)/5),int((i.max.x-i.min.x)/5),int((i.max.y-i.min.y)/5), outline="#000000", fill="#000000")
                              
        handler = lambda: onCloseOtherFrame(otherFrame)
        btn = Button(otherFrame, text="Close", command=handler)
        btn.pack()
        

if __name__ == "__main__":
    p = Painter()


    main_win = Tk()
    main_win.geometry("500x300")
    main_win.sourceFolder = ''
    main_win.sourceFile = ''


    def chooseFile():
        main_win.sourceFile = filedialog.askopenfilename(parent=main_win, initialdir= "/home/", title='Please select a directory')
    def hide():
        main_win.withdraw()
            
        #----------------------------------------------------------------------
    def openFrame():
        hide()
        p.prepare(main_win.sourceFile,False)
        p.Visualize();
    #----------------------------------------------------------------------
    def onCloseOtherFrame(otherFrame):
        otherFrame.destroy()
        show()
            
        #----------------------------------------------------------------------
    def show():
        main_win.update()
        main_win.deiconify()


    b_chooseFile = Button(main_win, text = "Chose File", width = 20, height = 3, command = chooseFile )
    b_chooseFile.place(x = 250,y = 50)
    b_chooseFile.width = 100

    b_chooseFile.pack()

    b_open = Button(main_win, text = "Continue to cutting", width = 20, height = 3, command = openFrame )
    b_open.place(x=500,y=300)
    b_open.width = 100
    b_open.pack()
    main_win.mainloop()
    sys.exit()

