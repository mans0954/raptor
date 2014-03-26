%define __jar_repack %{nil}

Name:          raptor-ica
Version:       0.1.0
Release:       1
Summary:       Information Collector Agent component of the Raptor Software Suite
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
and EZproxy. The ICA is the agent that resides alongside the system
to be monitored and analyses its log files for authentication events.
The ICA will then send this information to an MUA (raptor-mua) instance.



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
install -d %{buildroot}/opt/raptor/ica/conf
install -d %{buildroot}/opt/raptor/ica/keys
install -d %{buildroot}/opt/raptor/ica/lib
install -d %{buildroot}/opt/raptor/ica/logs

# Create directory for the initscript
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    install -d -m 0755 %{buildroot}%{_initrddir}
%if "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    install -d -m 0755 %{buildroot}/%{_sbindir}
%endif
%endif

# Install fixed-location files to correct locations
cp -r %{_builddir}/%{name}-%{version}/conf/* %{buildroot}/opt/raptor/ica/conf
cp -r %{_builddir}/%{name}-%{version}/keys/* %{buildroot}/opt/raptor/ica/keys
cp -r %{_builddir}/%{name}-%{version}/lib/* %{buildroot}/opt/raptor/ica/lib
cp -r %{_builddir}/%{name}-%{version}/%{name}.jar %{buildroot}/opt/raptor/ica

# Install initscript
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    install -m 0755 %{_builddir}/%{name}-%{version}/bin/raptoricad %{buildroot}%{_initrddir}/raptoricad
%if "%{_vendor}" == "suse"  || "%{_vendor}" == "Mandriva"
    %{__ln_s} -f %{_initrddir}/raptoricad %{buildroot}%{_sbindir}/rcraptoricad
%endif
%endif


 
%clean

# Remove everything in the buildroot
[ "%{buildroot}" != "/" ] && %{__rm} -rf %{buildroot}

 
 
%post

# Once the RPM is installed, key generation for this ICA instance should take place
# But only if installing, not ugprading!
if [ $1 == 1 ] ; then
    host=`hostname -f` 
    
    # Only run these commands if keytool exists
    if command -v keytool > /dev/null; then
        keytool -genkey -alias raptorica -keystore /opt/raptor/ica/keys/raptor-ica.jks -storepass changeit -keypass changeit -dname "CN=$host,ou=ICA,o=Raptor" -validity 7300 -keyalg RSA -keysize 2048
        keytool -export -alias raptorica -keystore /opt/raptor/ica/keys/raptor-ica.jks -storepass changeit -file /opt/raptor/ica/keys/raptor-ica-public.crt
    else
        echo ERROR: keytool command not found - automatic key generation for the Raptor ICA has not taken place and must be performed manually!
    fi
fi
 
# And then we want to add the service's initscript
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "Mandriva"
    # Add the service to the usual runlevels
    /sbin/chkconfig --add raptoricad
    
    # If upgrading, restart if the daemon is already running
    if [ $1 -gt 1 ] ; then
        /etc/init.d/raptoricad status 1>/dev/null && /etc/init.d/raptoricad restart 1>/dev/null
        exit 0
    fi
%endif
%if "%{_vendor}" == "suse"
    # Add the service to the usual runlevels
    cd /
    %insserv_force_if_yast raptoricad
%endif

 
%preun

# Stop the service before uninstalling
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "Mandriva"
    if [ $1 == 0 ] ; then
        /sbin/service raptoricad stop >/dev/null 2>&1
        /sbin/chkconfig --del raptoricad
    fi
%endif
%if "%{_vendor}" == "suse"
    %stop_on_removal raptoricad
%endif
exit 0

 
%postun

 
%files 
%defattr(-,root,root,-)
%dir /opt/raptor
%dir /opt/raptor/ica
%dir /opt/raptor/ica/conf
%dir /opt/raptor/ica/keys
%dir /opt/raptor/ica/lib
%dir /opt/raptor/ica/logs
%config(noreplace) /opt/raptor/ica/conf/event-release.xml
%config(noreplace) /opt/raptor/ica/conf/logging.xml
%config(noreplace) /opt/raptor/ica/conf/metadata.xml
%config(noreplace) /opt/raptor/ica/conf/server.properties
%config /opt/raptor/ica/conf/event-parse.xml
%config /opt/raptor/ica/conf/ica-core.xml
%config(noreplace) /opt/raptor/ica/keys/authorised-keys.jks
/opt/raptor/ica/lib/*.jar
/opt/raptor/ica/%{name}.jar
%if "%{_vendor}" == "redhat" || "%{_vendor}" == "suse" || "%{_vendor}" == "Mandriva"
    %config %{_initrddir}/raptoricad
%endif
%if "%{_vendor}" == "suse"
    %config %{_sbindir}/rcraptoricad
%endif

%changelog
* Mon May 16 2011  Rhys Smith <smith@cardiff.ac.uk>  0.1.0-1
- First version.