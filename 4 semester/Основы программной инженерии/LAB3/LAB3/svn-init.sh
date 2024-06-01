LAB3_dir=$(pwd)

cd ~
mkdir LAB3svn
cd LAB3svn || exit
svnadmin create repo
svn_dir=$(pwd)

mkdir main
cd "$svn_dir/main" || exit

svn mkdir "file://$svn_dir/repo/trunk" -m "Init trunk"
svn checkout "file://$svn_dir/repo/trunk"
cd "$svn_dir/main/trunk" || exit

cp -r /home/studs/s373440/LAB3/. /home/studs/s373440/LAB3svn/main/trunk

./gradlew lab-build

svn add --force .
svn commit -m "Init files of LAB3 project" --username=s373440
cd "$svn_dir/main/trunk" || exit
echo "Subversion initialization have been finished"