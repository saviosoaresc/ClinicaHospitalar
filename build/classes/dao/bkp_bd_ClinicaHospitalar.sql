PGDMP     5        
            {            ClinicaHospitalar    15.2    15.2     
           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16590    ClinicaHospitalar    DATABASE     �   CREATE DATABASE "ClinicaHospitalar" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
 #   DROP DATABASE "ClinicaHospitalar";
                postgres    false            �            1259    16613 	   consultas    TABLE       CREATE TABLE public.consultas (
    idpac integer NOT NULL,
    nomepac text NOT NULL,
    nomemed text NOT NULL,
    especializacao character varying(30) NOT NULL,
    diagnostico text,
    datacons character varying(10) NOT NULL,
    tipo text NOT NULL,
    valor text
);
    DROP TABLE public.consultas;
       public         heap    postgres    false            �            1259    16626    medicos    TABLE     �   CREATE TABLE public.medicos (
    numero_registro_medico integer NOT NULL,
    nome character varying(100),
    telefone character varying(15),
    especializacao character varying(50),
    lista_pacientes text
);
    DROP TABLE public.medicos;
       public         heap    postgres    false            �            1259    16625 "   medicos_numero_registro_medico_seq    SEQUENCE     �   CREATE SEQUENCE public.medicos_numero_registro_medico_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 9   DROP SEQUENCE public.medicos_numero_registro_medico_seq;
       public          postgres    false    218                       0    0 "   medicos_numero_registro_medico_seq    SEQUENCE OWNED BY     i   ALTER SEQUENCE public.medicos_numero_registro_medico_seq OWNED BY public.medicos.numero_registro_medico;
          public          postgres    false    217            �            1259    16623    numero_registro_paciente    SEQUENCE     �   CREATE SEQUENCE public.numero_registro_paciente
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.numero_registro_paciente;
       public          postgres    false            �            1259    16594 	   pacientes    TABLE       CREATE TABLE public.pacientes (
    numero_registro_paciente integer DEFAULT nextval('public.numero_registro_paciente'::regclass) NOT NULL,
    nome character varying(50) NOT NULL,
    telefone character varying(20),
    problema character varying(50),
    lista_consultas text
);
    DROP TABLE public.pacientes;
       public         heap    postgres    false    216            o           2604    16629    medicos numero_registro_medico    DEFAULT     �   ALTER TABLE ONLY public.medicos ALTER COLUMN numero_registro_medico SET DEFAULT nextval('public.medicos_numero_registro_medico_seq'::regclass);
 M   ALTER TABLE public.medicos ALTER COLUMN numero_registro_medico DROP DEFAULT;
       public          postgres    false    217    218    218                      0    16613 	   consultas 
   TABLE DATA           p   COPY public.consultas (idpac, nomepac, nomemed, especializacao, diagnostico, datacons, tipo, valor) FROM stdin;
    public          postgres    false    215   �                 0    16626    medicos 
   TABLE DATA           j   COPY public.medicos (numero_registro_medico, nome, telefone, especializacao, lista_pacientes) FROM stdin;
    public          postgres    false    218   �                 0    16594 	   pacientes 
   TABLE DATA           h   COPY public.pacientes (numero_registro_paciente, nome, telefone, problema, lista_consultas) FROM stdin;
    public          postgres    false    214   �                  0    0 "   medicos_numero_registro_medico_seq    SEQUENCE SET     Q   SELECT pg_catalog.setval('public.medicos_numero_registro_medico_seq', 12, true);
          public          postgres    false    217                       0    0    numero_registro_paciente    SEQUENCE SET     G   SELECT pg_catalog.setval('public.numero_registro_paciente', 22, true);
          public          postgres    false    216            s           2606    16633    medicos medicos_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.medicos
    ADD CONSTRAINT medicos_pkey PRIMARY KEY (numero_registro_medico);
 >   ALTER TABLE ONLY public.medicos DROP CONSTRAINT medicos_pkey;
       public            postgres    false    218            q           2606    16600    pacientes pacientes_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.pacientes
    ADD CONSTRAINT pacientes_pkey PRIMARY KEY (numero_registro_paciente);
 B   ALTER TABLE ONLY public.pacientes DROP CONSTRAINT pacientes_pkey;
       public            postgres    false    214            t           2606    16618    consultas consultas_idpac_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.consultas
    ADD CONSTRAINT consultas_idpac_fkey FOREIGN KEY (idpac) REFERENCES public.pacientes(numero_registro_paciente);
 H   ALTER TABLE ONLY public.consultas DROP CONSTRAINT consultas_idpac_fkey;
       public          postgres    false    3185    214    215                  x������ � �            x������ � �            x������ � �     