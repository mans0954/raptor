--
-- Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--         http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

    create table AuthenticationEvent (
        persistantId int8 not null,
        eventtime timestamp,
        serviceHost varchar(400),
        resourceHost varchar(255),
        serviceId varchar(255),
        resourceId varchar(255),
        eventId int4 not null,
        eventType varchar(255),
        resourceIdCategory int4,
        entityId varchar(255),
        serviceName varchar(255),
        organisationName varchar(255),
        authenticationType varchar(255),
        principalName varchar(255),
        school varchar(255),
        affiliation varchar(255),
        primary key (persistantId)
    );

    create table EzproxyAuthenticationEvent (
        persistantId int8 not null,
        eventtime timestamp,
        serviceHost varchar(400),
        resourceHost varchar(255),
        serviceId varchar(255),
        resourceId varchar(255),
        eventId int4 not null,
        eventType varchar(255),
        resourceIdCategory int4,
        entityId varchar(255),
        serviceName varchar(255),
        organisationName varchar(255),
        authenticationType varchar(255),
        principalName varchar(255),
        school varchar(255),
        affiliation varchar(255),
        requesterIp varchar(255),
        sessionId varchar(255),
        current_hashCode int4,
        primary key (persistantId)
    );

    create table RadiusAuthenticationEvent (
        persistantId int8 not null,
        eventtime timestamp,
        serviceHost varchar(400),
        resourceHost varchar(255),
        serviceId varchar(255),
        resourceId varchar(255),
        eventId int4 not null,
        eventType varchar(255),
        resourceIdCategory int4,
        entityId varchar(255),
        serviceName varchar(255),
        organisationName varchar(255),
        authenticationType varchar(255),
        principalName varchar(255),
        school varchar(255),
        affiliation varchar(255),
        clientIdentifier varchar(255),
        primary key (persistantId)
    );

    create table ShibbolethIdpAuthenticationEvent (
        persistantId int8 not null,
        eventtime timestamp,
        serviceHost varchar(400),
        resourceHost varchar(255),
        serviceId varchar(255),
        resourceId varchar(255),
        eventId int4 not null,
        eventType varchar(255),
        resourceIdCategory int4,
        entityId varchar(255),
        serviceName varchar(255),
        organisationName varchar(255),
        authenticationType varchar(255),
        principalName varchar(255),
        school varchar(255),
        affiliation varchar(255),
        requestID varchar(255),
        messageProfileId varchar(255),
        responseBinding varchar(255),
        requestBinding varchar(255),
        releasedattributes varchar(400),
        assertionid varchar(255),
        nameIdentifier varchar(255),
        responseId varchar(255),
        current_hashCode int4,
        primary key (persistantId)
    );

    create table ShibbolethSpAuthenticationEvent (
        persistantId int8 not null,
        eventtime timestamp,
        serviceHost varchar(400),
        resourceHost varchar(255),
        serviceId varchar(255),
        resourceId varchar(255),
        eventId int4 not null,
        eventType varchar(255),
        resourceIdCategory int4,
        entityId varchar(255),
        serviceName varchar(255),
        organisationName varchar(255),
        authenticationType varchar(255),
        principalName varchar(255),
        school varchar(255),
        affiliation varchar(255),
        protocol varchar(255),
        clientIp varchar(255),
        sessionId varchar(255),
        primary key (persistantId)
    );

    create table event (
        persistantId int8 not null,
        eventtime timestamp,
        serviceHost varchar(400),
        resourceHost varchar(255),
        serviceId varchar(255),
        resourceId varchar(255),
        eventId int4 not null,
        eventType varchar(255),
        resourceIdCategory int4,
        entityId varchar(255),
        serviceName varchar(255),
        organisationName varchar(255),
        primary key (persistantId)
    );

    create table latestEqualEntries (
        latestEqualEntries_id int8 not null,
        hashcode int4
    );

    create table release_information (
        persistantId int8 not null,
        serviceEndpoint varchar(255) not null,
        lastReleasedEventTime timestamp,
        primary key (persistantId)
    );

    create table resource_metadata (
        persistantId int8 not null,
        resourceId varchar(255),
        internal bool,
        external bool,
        primary key (persistantId)
    );

    create index AuthenticationEventeventid_i on AuthenticationEvent (eventId);

    create index AuthenticationEventorgname_i on AuthenticationEvent (organisationName);

    create index AuthenticationEventserviceid_i on AuthenticationEvent (serviceId);

    create index AuthenticationEventservicename_i on AuthenticationEvent (serviceName);

    create index AuthenticationEventrequesthost_i on AuthenticationEvent (serviceHost);

    create index AuthenticationEvententityid_i on AuthenticationEvent (entityId);

    create index AuthenticationEventdt_i on AuthenticationEvent (eventtime);

    create index affiliation_i on AuthenticationEvent (affiliation);

    create index principalname_i on AuthenticationEvent (principalName);

    create index school_i on AuthenticationEvent (school);

    create index EzproxyAuthenticationEventAuthenticationEventeventid_i on EzproxyAuthenticationEvent (eventId);

    create index EzproxyAuthenticationEventAuthenticationEventorgname_i on EzproxyAuthenticationEvent (organisationName);

    create index EzproxyAuthenticationEventAuthenticationEventserviceid_i on EzproxyAuthenticationEvent (serviceId);

    create index EzproxyAuthenticationEventAuthenticationEventservicename_i on EzproxyAuthenticationEvent (serviceName);

    create index EzproxyAuthenticationEventAuthenticationEventrequesthost_i on EzproxyAuthenticationEvent (serviceHost);

    create index EzproxyAuthenticationEventAuthenticationEvententityid_i on EzproxyAuthenticationEvent (entityId);

    create index EzproxyAuthenticationEventAuthenticationEventdt_i on EzproxyAuthenticationEvent (eventtime);

    create index EzproxyAuthenticationEventaffiliation_i on EzproxyAuthenticationEvent (affiliation);

    create index EzproxyAuthenticationEventprincipalname_i on EzproxyAuthenticationEvent (principalName);

    create index EzproxyAuthenticationEventschool_i on EzproxyAuthenticationEvent (school);

    create index hashcode_i on EzproxyAuthenticationEvent (current_hashCode);

    create index RadiusAuthenticationEventAuthenticationEventeventid_i on RadiusAuthenticationEvent (eventId);

    create index RadiusAuthenticationEventAuthenticationEventorgname_i on RadiusAuthenticationEvent (organisationName);

    create index RadiusAuthenticationEventAuthenticationEventserviceid_i on RadiusAuthenticationEvent (serviceId);

    create index RadiusAuthenticationEventAuthenticationEventservicename_i on RadiusAuthenticationEvent (serviceName);

    create index RadiusAuthenticationEventAuthenticationEventrequesthost_i on RadiusAuthenticationEvent (serviceHost);

    create index RadiusAuthenticationEventAuthenticationEvententityid_i on RadiusAuthenticationEvent (entityId);

    create index RadiusAuthenticationEventAuthenticationEventdt_i on RadiusAuthenticationEvent (eventtime);

    create index RadiusAuthenticationEventaffiliation_i on RadiusAuthenticationEvent (affiliation);

    create index RadiusAuthenticationEventprincipalname_i on RadiusAuthenticationEvent (principalName);

    create index RadiusAuthenticationEventschool_i on RadiusAuthenticationEvent (school);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventeventid_i on ShibbolethIdpAuthenticationEvent (eventId);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventorgname_i on ShibbolethIdpAuthenticationEvent (organisationName);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventserviceid_i on ShibbolethIdpAuthenticationEvent (serviceId);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventservicename_i on ShibbolethIdpAuthenticationEvent (serviceName);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventrequesthost_i on ShibbolethIdpAuthenticationEvent (serviceHost);

    create index ShibbolethIdpAuthenticationEventAuthenticationEvententityid_i on ShibbolethIdpAuthenticationEvent (entityId);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventdt_i on ShibbolethIdpAuthenticationEvent (eventtime);

    create index ShibbolethIdpAuthenticationEventaffiliation_i on ShibbolethIdpAuthenticationEvent (affiliation);

    create index ShibbolethIdpAuthenticationEventprincipalname_i on ShibbolethIdpAuthenticationEvent (principalName);

    create index ShibbolethIdpAuthenticationEventschool_i on ShibbolethIdpAuthenticationEvent (school);

    create index hashcode_i_shib on ShibbolethIdpAuthenticationEvent (current_hashCode);

    create index ShibbolethSpAuthenticationEventAuthenticationEventeventid_i on ShibbolethSpAuthenticationEvent (eventId);

    create index ShibbolethSpAuthenticationEventAuthenticationEventorgname_i on ShibbolethSpAuthenticationEvent (organisationName);

    create index ShibbolethSpAuthenticationEventAuthenticationEventserviceid_i on ShibbolethSpAuthenticationEvent (serviceId);

    create index ShibbolethSpAuthenticationEventAuthenticationEventservicename_i on ShibbolethSpAuthenticationEvent (serviceName);

    create index ShibbolethSpAuthenticationEventAuthenticationEventrequesthost_i on ShibbolethSpAuthenticationEvent (serviceHost);

    create index ShibbolethSpAuthenticationEventAuthenticationEvententityid_i on ShibbolethSpAuthenticationEvent (entityId);

    create index ShibbolethSpAuthenticationEventAuthenticationEventdt_i on ShibbolethSpAuthenticationEvent (eventtime);

    create index ShibbolethSpAuthenticationEventaffiliation_i on ShibbolethSpAuthenticationEvent (affiliation);

    create index ShibbolethSpAuthenticationEventprincipalname_i on ShibbolethSpAuthenticationEvent (principalName);

    create index ShibbolethSpAuthenticationEventschool_i on ShibbolethSpAuthenticationEvent (school);

    create index eventid_i on event (eventId);

    create index orgname_i on event (organisationName);

    create index serviceid_i on event (serviceId);

    create index servicename_i on event (serviceName);

    create index requesthost_i on event (serviceHost);

    create index entityid_i on event (entityId);

    create index dt_i on event (eventtime);

    alter table latestEqualEntries 
        add constraint FK283FC3E3AAC8B6DE 
        foreign key (latestEqualEntries_id) 
        references release_information;

    create index serviceid_index on resource_metadata (external);

    create index requesthost_index on resource_metadata (resourceId);

    create sequence hib_release_information_seq;

    create sequence hib_resource_metadata_seq;

    create table hibernate_unique_key (
         next_hi int4 
    );

    insert into hibernate_unique_key values ( 0 );
