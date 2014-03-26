%define __jar_repack %{nil}

Name:          raptor-mua
Version:       1.1.3
Release:       1
Summary:       Multi Unit Aggregator component of the Raptor Software Suite
Group:         Productivity/Other
License:       Apache 2.0
URL:           http://iam.cf.ac.uk/Raptor
Source:        %{name}-%{version}.zip
BuildArch:     noarch
BuildRoot:     %{_tmppath}/%{name}-%{version}-build

BuildRequires: unzip
Requires:      java >= 1.6.0



%description
Raptor is a software tool designed to report upon authentication
information for access management systems such as the Shibboleth IdP
and EZproxy. The MUA is the service that listens for incoming event
information from ICA (raptor-ica) instances. It is also the service
that the Web interface (raptor-web) connects to.



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
install -d %{buildroot}/opt/raptor/mua/batchimport
install -d %{buildroot}/opt/raptor/mua/batchimport/shib2
install -d %{buildroot}/opt/raptor/mua/batchimport/shib13
install -d %{buildroot}/opt/raptor/mua/batchimport/ezproxy
install -d %{buildroot}/opt/raptor/mua/batchimport/shibsp_upto24
install -d %{buildroot}/opt/raptor/mua/conf
install -d %{buildroot}/opt/raptor/mua/data
install -d %{buildroot}/opt/raptor/mua/keys
install -d %{buildroot}/opt/raptor/mua/lib
install -d %{buildroot}/opt/raptor/mua/logs

# Create directory for the initscript
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    install -d -m 0755 %{buildroot}%{_initrddir}
%if "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    install -d -m 0755 %{buildroot}/%{_sbindir}
%endif
%endif

# Install fixed-location files to correct locations
cp -r %{_builddir}/%{name}-%{version}/conf/* %{buildroot}/opt/raptor/mua/conf
cp -r %{_builddir}/%{name}-%{version}/keys/* %{buildroot}/opt/raptor/mua/keys
cp -r %{_builddir}/%{name}-%{version}/lib/* %{buildroot}/opt/raptor/mua/lib
cp -r %{_builddir}/%{name}-%{version}/%{name}.jar %{buildroot}/opt/raptor/mua

# Install initscript
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    install -m 0755 %{_builddir}/%{name}-%{version}/bin/raptormuad %{buildroot}%{_initrddir}/raptormuad
%if "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    %{__ln_s} -f %{_initrddir}/raptormuad %{buildroot}%{_sbindir}/rcraptormuad
%endif
%endif


 
%clean

# Remove everything in the buildroot
[ "%{buildroot}" != "/" ] && %{__rm} -rf %{buildroot}

 
 
%post

# Once the RPM is installed, key generation for this MUA instance should take place
# But only if installing, not ugprading!
if [ $1 == 1 ] ; then
    host=`hostname -f` 

   # Only run these commands if keytool exists
    if command -v keytool > /dev/null; then
        keytool -genkey -alias raptormua -keystore /opt/raptor/mua/keys/raptor-mua.jks -storepass changeit -keypass changeit -dname "CN=$host,ou=MUA,o=Raptor" -validity 7300 -keyalg RSA -keysize 2048
        keytool -export -alias raptormua -keystore /opt/raptor/mua/keys/raptor-mua.jks -storepass changeit -file /opt/raptor/mua/keys/raptor-mua-public.crt
    else
        echo ERROR: keytool command not found - automatic key generation for the Raptor MUA has not taken place and must be performed manually!
    fi
fi
 
# And then we want to add the service's initscript
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "Mandriva"
    # Add the service to the usual runlevels
    /sbin/chkconfig --add raptormuad
    
    # If upgrading, restart if the daemon is already running
    if [ $1 -gt 1 ] ; then
        /etc/init.d/raptormuad status 1>/dev/null && /etc/init.d/raptormuad restart 1>/dev/null
        exit 0
    fi
%endif
%if "%{_vendor}" == "suse"
    # Add the service to the usual runlevels
    cd /
    %insserv_force_if_yast raptormuad
%endif

 
%preun

# Stop the service before uninstalling
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "Mandriva"
    if [ $1 == 0 ] ; then
        /sbin/service raptormuad stop >/dev/null 2>&1
        /sbin/chkconfig --del raptormuad
    fi
%endif
%if "%{_vendor}" == "suse"
    %stop_on_removal raptormuad
%endif
exit 0

 
%postun

 
%files 
%defattr(-,root,root,-)
%dir /opt/raptor
%dir /opt/raptor/mua
%dir /opt/raptor/mua/batchimport
%dir /opt/raptor/mua/conf
%dir /opt/raptor/mua/data
%dir /opt/raptor/mua/keys
%dir /opt/raptor/mua/lib
%dir /opt/raptor/mua/logs
%dir /opt/raptor/mua/batchimport/shib2
%dir /opt/raptor/mua/batchimport/shib13
%dir /opt/raptor/mua/batchimport/shibsp_upto24
%dir /opt/raptor/mua/batchimport/ezproxy
%config(noreplace) /opt/raptor/mua/conf/attribute-association.xml
%config(noreplace) /opt/raptor/mua/conf/batch-event-parse.xml
%config(noreplace) /opt/raptor/mua/conf/batch-event-parse-formats-custom.xml
%config            /opt/raptor/mua/conf/batch-event-parse-formats-system.xml
%config(noreplace) /opt/raptor/mua/conf/database.xml
%config            /opt/raptor/mua/conf/event-release.xml
%config(noreplace) /opt/raptor/mua/conf/logging.xml
%config(noreplace) /opt/raptor/mua/conf/metadata.xml
%config            /opt/raptor/mua/conf/mua-core.xml
%config(noreplace) /opt/raptor/mua/conf/server.properties
%config            /opt/raptor/mua/conf/statistical-processors.xml
%config(noreplace) /opt/raptor/mua/conf/statistical-units-custom.xml
%config            /opt/raptor/mua/conf/statistical-units-system.xml
%config(noreplace) /opt/raptor/mua/conf/users.xml
%config            /opt/raptor/mua/conf/web.xml
%config(noreplace) /opt/raptor/mua/keys/authorised-keys.jks
/opt/raptor/mua/lib/*.jar
/opt/raptor/mua/%{name}.jar
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    %config %{_initrddir}/raptormuad
%endif
%if "%{_vendor}" == "suse"
    %{_sbindir}/rcraptormuad
%endif

%changelog
* Sat Aug 25 2012  Rhys Smith <smith@cardiff.ac.uk>  1.1.3-1
- Some more bug fixes, to v1.1.2. See release notes for details.

* Fri Aug 24 2012  Rhys Smith <smith@cardiff.ac.uk>  1.1.2-1
- Some more bug fixes, to v1.1.1. See release notes for details.

* Fri Aug 10 2012  Rhys Smith <smith@cardiff.ac.uk>  1.1.1-1
- Some bug fixes to v1.1.1. See release notes for details.

* Fri Jul 6 2012  Rhys Smith <smith@cardiff.ac.uk>  1.1.0-1
- First release of v1.1. See release notes for details.

* Mon Dec 19 2011  Rhys Smith <smith@cardiff.ac.uk>  1.0.1-1
- Minor bugfix to v1.0.0 - fixed one or two bugs.

* Mon Dec 05 2011  Rhys Smith <smith@cardiff.ac.uk>  1.0.0-1
- First release of v1. Some changed config files.

* Wed Sep 28 2011  Rhys Smith <smith@cardiff.ac.uk>  0.2.1-1
- Bumping version for v0.2.1 release which fixes some mySQL issues.

* Sat Sep 17 2011  Rhys Smith <smith@cardiff.ac.uk>  0.2.0-1
- Bumping version for v0.2.0 release.

* Fri Jun 3 2011  Rhys Smith <smith@cardiff.ac.uk>  0.1.1-1
- Changed rev for v0.1.1 release. 

* Mon May 16 2011  Rhys Smith <smith@cardiff.ac.uk>  0.1.0-1
- First version.