while read;do 
	mvn package war:inplace
	rm webapp/WEB-INF/classes/ejb -r;
	touch webapp/WEB-INF/web.xml
done
