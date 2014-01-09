summary: DataX guqiang can write data to guqiang
Name: t_dp_datax_guqiangwriter
Version: 1.0.0
Release: 1
Group: System
License: GPL
BuildArch: noarch
AutoReqProv: no
Requires: t_dp_datax_engine

%define dataxpath /home/taobao/datax

%description
DataX guqiang can write data to guqiang

%prep
cd ${OLDPWD}/../
export LANG=zh_CN.UTF-8
ant dist

%build

%install
mkdir -p %{dataxpath}/plugins/writer/guqiangwriter

cp ${OLDPWD}/../src/com/taobao/datax/plugins/writer/guqiangwriter/ParamKey.java %{dataxpath}/plugins/writer/guqiangwriter
cp ${OLDPWD}/../build/plugins/guqiangwriter-1.0.0.jar %{dataxpath}/plugins/writer/guqiangwriter
cp ${OLDPWD}/../build/plugins/plugins-common-1.0.0.jar %{dataxpath}/plugins/writer/guqiangwriter
cp ${OLDPWD}/../build/plugins/plugins-common-http-1.0.0.jar %{dataxpath}/plugins/writer/guqiangwriter
cp ${OLDPWD}/../build/plugins/plugins-common-oauth-1.0.0.jar %{dataxpath}/plugins/writer/guqiangwriter

cp -r ${OLDPWD}/../libs/mysql-connector-java-5.1.18-bin.jar %{dataxpath}/plugins/writer/guqiangwriter
cp -r ${OLDPWD}/../libs/commons-dbcp-1.4.jar %{dataxpath}/plugins/writer/guqiangwriter
cp -r ${OLDPWD}/../libs/commons-pool-1.5.4.jar %{dataxpath}/plugins/writer/guqiangwriter
cp -r ${OLDPWD}/../libs/commons-logging-1.1.3.jar %{dataxpath}/plugins/writer/guqiangwriter
cp -r ${OLDPWD}/../libs/json-lib-2.3-jdk15.jar %{dataxpath}/plugins/writer/guqiangwriter
cp -r ${OLDPWD}/../libs/ezmorph-1.0.6.jar %{dataxpath}/plugins/writer/guqiangwriter
cp -r ${OLDPWD}/../libs/commons-beanutils-1.8.3.jar %{dataxpath}/plugins/writer/guqiangwriter
cp -r ${OLDPWD}/../libs/commons-collections-3.2.1.jar %{dataxpath}/plugins/writer/guqiangwriter
cp -r ${OLDPWD}/../libs/httpclient-4.2.3.jar %{dataxpath}/plugins/writer/guqiangwriter
cp -r ${OLDPWD}/../libs/httpcore-4.2.2.jar %{dataxpath}/plugins/writer/guqiangwriter
cp -r ${OLDPWD}/../libs/jdom-1.0.jar %{dataxpath}/plugins/writer/guqiangwriter


%files
%defattr(0755,root,root)
%{dataxpath}/plugins/writer/guqiangwriter

%changelog
* Fri Aug 20 2010 meining 
- Version 1.0.0


