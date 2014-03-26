Name:          raptor-server
Version:       1.0.0
Release:       1
Summary:       MUA and Web components of the Raptor Software Suite
Group:         Productivity/Other
License:       Apache 2.0
URL:           http://iam.cf.ac.uk/Raptor
Source:        %{name}-%{version}.zip
BuildArch:     noarch
BuildRoot:     %{_tmppath}/%{name}-%{version}-build

BuildRequires: unzip
Requires:      java >= 1.6.0
Requires:      raptor-mua = 0.1.0
Requires:      raptor-web = 0.1.0



%description
Raptor is a software tool designed to report upon authentication
information for access management systems such as the Shibboleth IdP
and EZproxy. This is a metapackage that installs the MUA and Web
components of Raptor.



%prep

# Remove anything that already exists, unzip source, and cd into it
%{__rm} -rf %{name}-%{version}
%{__unzip} -q %{_sourcedir}/%{name}-%{version}.zip
cd %{name}-%{version}


 
%build
# Nothing to do


 
%install

# Create fixed-location directories in /opt
install -d %{buildroot}/opt/raptor/server

# Install fixed-location files to correct locations
cp -r %{_builddir}/%{name}-%{version}/version.txt %{buildroot}/opt/raptor/server/version.txt


 
%clean

# Remove everything in the buildroot
[ "%{buildroot}" != "/" ] && %{__rm} -rf %{buildroot}

 
 
%post

# Once the RPMs are installed, key exchange between each component should take place
# But only if installing, not ugprading!
if [ $1 == 1 ] ; then

    # Only run these commands if keytool exists
    if command -v keytool > /dev/null; then

        # Swap keys between MUA and Web
        keytool -import -noprompt -keystore /opt/raptor/web/keys/authorised-keys.jks -storepass changeit -alias raptormua -file /opt/raptor/mua/keys/raptor-mua-public.crt
        keytool -import -noprompt -keystore /opt/raptor/mua/keys/authorised-keys.jks -storepass changeit -alias raptorweb -file /opt/raptor/web/keys/raptor-web-public.crt
    else
        echo ERROR: keytool command not found - automatic certificate exchange has not taken place and must be performed manually!
    fi
fi 
 
 
 
%preun


 
%postun

 
%files 

%defattr(-,root,root,-)
%dir /opt/raptor
%dir /opt/raptor/server
/opt/raptor/server/version.txt

%changelog
* Mon May 16 2011  Rhys Smith <smith@cardiff.ac.uk>  1.0.0-1
- First version.