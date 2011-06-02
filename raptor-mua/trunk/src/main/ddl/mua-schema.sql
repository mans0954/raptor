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
        serviceHost varchar(255),
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
        serviceHost varchar(255),
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

    create table ShibbolethIdpAuthenticationEvent (
        persistantId int8 not null,
        eventtime timestamp,
        serviceHost varchar(255),
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
        releasedAttributes varchar(255),
        assertionId varchar(255),
        nameIdentifier varchar(255),
        responseId varchar(255),
        current_hashCode int4,
        primary key (persistantId)
    );

    create table event (
        persistantId int8 not null,
        eventtime timestamp,
        serviceHost varchar(255),
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

    create table release_information (
        persistantId int8 not null,
        serviceEndpoint varchar(255) not null,
        lastReleasedEventTime timestamp,
        primary key (persistantId)
    );

    create table latestequalentries (
        latestEqualEntries_id int8 not null,
        hashcode int4
    );

    create table resource_metadata (
        persistantId int8 not null,
        resourceId varchar(255),
        internal bool,
        external bool,
        primary key (persistantId)
    );

    create index AuthenticationEventdt_index on AuthenticationEvent (eventtime);

    create index AuthenticationEventrequesthost_indx on AuthenticationEvent (serviceHost);

    create index AuthenticationEventeventid_indx on AuthenticationEvent (eventId);

    create index AuthenticationEventserviceid_indx on AuthenticationEvent (serviceId);

    create index AuthenticationEventorganisationName_indx on AuthenticationEvent (organisationName);

    create index AuthenticationEventserviceName_indx on AuthenticationEvent (serviceName);

    create index AuthenticationEvententityId_index on AuthenticationEvent (entityId);

    create index affiliation_indx on AuthenticationEvent (affiliation);

    create index principalname_indx on AuthenticationEvent (principalName);

    create index school_index on AuthenticationEvent (school);

    create index EzproxyAuthenticationEventAuthenticationEventdt_index on EzproxyAuthenticationEvent (eventtime);

    create index EzproxyAuthenticationEventAuthenticationEventrequesthost_indx on EzproxyAuthenticationEvent (serviceHost);

    create index EzproxyAuthenticationEventAuthenticationEventeventid_indx on EzproxyAuthenticationEvent (eventId);

    create index EzproxyAuthenticationEventAuthenticationEventserviceid_indx on EzproxyAuthenticationEvent (serviceId);

    create index EzproxyAuthenticationEventAuthenticationEventorganisationName_indx on EzproxyAuthenticationEvent (organisationName);

    create index EzproxyAuthenticationEventAuthenticationEventserviceName_indx on EzproxyAuthenticationEvent (serviceName);

    create index EzproxyAuthenticationEventAuthenticationEvententityId_index on EzproxyAuthenticationEvent (entityId);

    create index EzproxyAuthenticationEventaffiliation_indx on EzproxyAuthenticationEvent (affiliation);

    create index EzproxyAuthenticationEventprincipalname_indx on EzproxyAuthenticationEvent (principalName);

    create index EzproxyAuthenticationEventschool_index on EzproxyAuthenticationEvent (school);

    create index hashcode_indx on EzproxyAuthenticationEvent (current_hashCode);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventdt_index on ShibbolethIdpAuthenticationEvent (eventtime);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventrequesthost_indx on ShibbolethIdpAuthenticationEvent (serviceHost);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventeventid_indx on ShibbolethIdpAuthenticationEvent (eventId);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventserviceid_indx on ShibbolethIdpAuthenticationEvent (serviceId);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventorganisationName_indx on ShibbolethIdpAuthenticationEvent (organisationName);

    create index ShibbolethIdpAuthenticationEventAuthenticationEventserviceName_indx on ShibbolethIdpAuthenticationEvent (serviceName);

    create index ShibbolethIdpAuthenticationEventAuthenticationEvententityId_index on ShibbolethIdpAuthenticationEvent (entityId);

    create index ShibbolethIdpAuthenticationEventaffiliation_indx on ShibbolethIdpAuthenticationEvent (affiliation);

    create index ShibbolethIdpAuthenticationEventprincipalname_indx on ShibbolethIdpAuthenticationEvent (principalName);

    create index ShibbolethIdpAuthenticationEventschool_index on ShibbolethIdpAuthenticationEvent (school);

    create index hashcode_indx on ShibbolethIdpAuthenticationEvent (current_hashCode);

    create index dt_index on event (eventtime);

    create index requesthost_indx on event (serviceHost);

    create index eventid_indx on event (eventId);

    create index serviceid_indx on event (serviceId);

    create index organisationName_indx on event (organisationName);

    create index serviceName_indx on event (serviceName);

    create index entityId_index on event (entityId);

    alter table latestequalentries 
        add constraint FK94BC350EAAC8B6DE 
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
