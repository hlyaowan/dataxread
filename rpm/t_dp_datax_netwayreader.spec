summary: DataX netwayreader can read data from http
Name: t_dp_datax_netwayreader
Version: 1.0.0
Release: 1
Group: System
License: GPL
BuildArch: noarch
AutoReqProv: no 
Requires: t_dp_datax_engine

%define dataxpath /home/taobao/datax

%description
DataX netwayreader can read data from http


%prep
cd ${OLDPWD}/../
export LANG=zh_CN.UTF-8
ant dist

%build

%install
mkdir -p %{dataxpath}/plugins/reader/netwayreader

cp ${OLDPWD}/../src/com/taobao/datax/plugins/reader/netwayreader/ParamKey.java %{dataxpath}/plugins/reader/netwayreader
cp ${OLDPWD}/../build/plugins/netwayreader-1.0.0.jar %{dataxpath}/plugins/reader/netwayreader
cp ${OLDPWD}/../build/plugins/plugins-common-1.0.0.jar %{dataxpath}/plugins/reader/netwayreader
cp ${OLDPWD}/../build/plugins/plugins-common-http-1.0.0.jar %{dataxpath}/plugins/reader/netwayreader
cp ${OLDPWD}/../build/plugins/plugins-common-oauth-1.0.0.jar %{dataxpath}/plugins/reader/netwayreader


cp -r ${OLDPWD}/../libs/json-lib-2.3-jdk15.jar %{dataxpath}/plugins/reader/netwayreader
cp -r ${OLDPWD}/../libs/ezmorph-1.0.6.jar %{dataxpath}/plugins/reader/netwayreader
cp -r ${OLDPWD}/../libs/commons-beanutils-1.8.3.jar %{dataxpath}/plugins/reader/netwayreader
cp -r ${OLDPWD}/../libs/commons-collections-3.2.1.jar %{dataxpath}/plugins/reader/netwayreader
cp -r ${OLDPWD}/../libs/httpclient-4.2.3.jar %{dataxpath}/plugins/reader/netwayreader
cp -r ${OLDPWD}/../libs/httpcore-4.2.2.jar %{dataxpath}/plugins/reader/netwayreader
cp -r ${OLDPWD}/../libs/sunjce_provider.jar %{dataxpath}/plugins/reader/netwayreader
cp -r ${OLDPWD}/../libs/jdom-1.0.jar %{dataxpath}/plugins/reader/netwayreader



%files
%defattr(0755,root,root)
%{dataxpath}/plugins/reader/netwayreader

%changelog
* Fri Aug 12 2011 hejianchao.pt
- Version 1.0.0
