%define __jar_repack %{nil}

Name:          raptor-web
Version:       1.0.1
Release:       1
Summary:       Web interface component of the Raptor Software Suite
Group:         Productivity/Other
License:       Apache 2.0
URL:           http://iam.cf.ac.uk/Raptor
Source:        %{name}-%{version}.zip
BuildArch:     noarch
BuildRoot:     %{_tmppath}/%{name}-%{version}-build

BuildRequires: unzip
Requires:      java >= 1.6.0
Requires:      fontconfig



%description
Raptor is a software tool designed to report upon authentication
information for access management systems such as the Shibboleth IdP
and EZproxy. The Web interface is the service that connect to an MUA
(raptor-mia) instance, allowing users to view and query its data.


%prep

# Remove anything that already exists, unzip source, and cd into it
%{__rm} -rf %{name}-%{version}
%{__unzip} -q %{_sourcedir}/%{name}-%{version}.zip
cd %{name}-%{version}


 
%build
# Nothing to do


 
%install

# Suppress the java 1.5 bytecode verion error when compiling for SLE on the OSBS
export NO_BRP_CHECK_BYTECODE_VERSION=true

# Create fixed-location directories in /opt
install -d %{buildroot}/opt/raptor/web/conf
install -d %{buildroot}/opt/raptor/web/keys
install -d %{buildroot}/opt/raptor/web/lib
install -d %{buildroot}/opt/raptor/web/logs
install -d %{buildroot}/opt/raptor/web/webapp


# Create directory for the initscript
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    install -d -m 0755 %{buildroot}%{_initrddir}
%if "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    install -d -m 0755 %{buildroot}/%{_sbindir}
%endif
%endif

# Install fixed-location files to correct locations
cp -r %{_builddir}/%{name}-%{version}/conf/* %{buildroot}/opt/raptor/web/conf
cp -r %{_builddir}/%{name}-%{version}/keys/* %{buildroot}/opt/raptor/web/keys
cp -r %{_builddir}/%{name}-%{version}/lib/* %{buildroot}/opt/raptor/web/lib
cp -r %{_builddir}/%{name}-%{version}/webapp/* %{buildroot}/opt/raptor/web/webapp
cp -r %{_builddir}/%{name}-%{version}/%{name}.jar %{buildroot}/opt/raptor/web

# Install initscript
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    install -m 0755 %{_builddir}/%{name}-%{version}/bin/raptorwebd %{buildroot}%{_initrddir}/raptorwebd
%if "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    %{__ln_s} -f %{_initrddir}/raptorwebd %{buildroot}%{_sbindir}/rcraptorwebd
%endif
%endif


 
%clean

# Remove everything in the buildroot
[ "%{buildroot}" != "/" ] && %{__rm} -rf %{buildroot}

 
 
%post

# Once the RPM is installed, key generation for this Web instance should take place
# But only if installing, not ugprading!
if [ $1 == 1 ] ; then
    host=`hostname -f` 

    # Only run these commands if keytool exists
    if command -v keytool > /dev/null; then
        keytool -genkey -alias raptorweb -keystore /opt/raptor/web/keys/raptor-web.jks -storepass changeit -keypass changeit -dname "CN=$host,ou=WEB,o=Raptor" -validity 7300 -keyalg RSA -keysize 2048
        keytool -export -alias raptorweb -keystore /opt/raptor/web/keys/raptor-web.jks -storepass changeit -file /opt/raptor/web/keys/raptor-web-public.crt
    else
        echo ERROR: keytool command not found - automatic key generation for Raptor Web has not taken place and must be performed manually!
    fi
fi
 
# And then we want to add the service's initscript
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "Mandriva"
    # Add the service to the usual runlevels
    /sbin/chkconfig --add raptorwebd
    
    # If upgrading, restart if the daemon is already running
    if [ $1 -gt 1 ] ; then
        /etc/init.d/raptorwebd status 1>/dev/null && /etc/init.d/raptorwebd restart 1>/dev/null
        exit 0
    fi
%endif
%if "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    # Add the service to the usual runlevels
    cd /
    %insserv_force_if_yast raptorwebd
%endif

 
%preun

# Stop the service before uninstalling
%if "%{_vendor}" == "redhat"
    if [ $1 == 0 ] ; then
        /sbin/service raptorwebd stop >/dev/null 2>&1
        /sbin/chkconfig --del raptorwebd
    fi
%endif
%if "%{_vendor}" == "suse"
    %stop_on_removal raptorwebd
%endif
exit 0

 
%postun

 
%files 
%defattr(-,root,root,-)
%dir /opt/raptor
%dir /opt/raptor/web
%dir /opt/raptor/web/conf
%dir /opt/raptor/web/keys
%dir /opt/raptor/web/lib
%dir /opt/raptor/web/logs
/opt/raptor/web/webapp
%config            /opt/raptor/web/conf/dashboard-statistics.xml
%config(noreplace) /opt/raptor/web/conf/logging.xml
%config(noreplace) /opt/raptor/web/conf/metadata.xml
%config(noreplace) /opt/raptor/web/conf/mua-endpoints.xml
%config(noreplace) /opt/raptor/web/conf/server.properties
%config(noreplace) /opt/raptor/web/conf/users.xml
%config            /opt/raptor/web/conf/web-core.xml
%config(noreplace) /opt/raptor/web/keys/authorised-keys.jks
/opt/raptor/web/lib/*.jar
/opt/raptor/web/%{name}.jar
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "suse"
    %config %{_initrddir}/raptorwebd
%endif
%if "%{_vendor}" == "suse"
    %{_sbindir}/rcraptorwebd
%endif

%changelog
* Mon Dec 19 2011  Rhys Smith <smith@cardiff.ac.uk>  1.0.1-1
- Minor bugfix to v1.0.0 - fixed one or two bugs.

* Mon Dec 05 2011  Rhys Smith <smith@cardiff.ac.uk>  1.0.0-1
- First release of v1.

* Sat Sep 17 2011  Rhys Smith <smith@cardiff.ac.uk>  0.2.0-1
- Bumping version for v0.2.0 release. Added dependency of fontconfig.

* Fri Jun 3 2011  Rhys Smith <smith@cardiff.ac.uk>  0.1.1-1
- Changed rev for v0.1.1 release. 

* Mon May 16 2011  Rhys Smith <smith@cardiff.ac.uk>  0.1.0-1
- First version.