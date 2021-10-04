
{{- define "kube-hello-app.name" -}}
  {{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}


{{- define "kube-hello-app.fullname" -}}
  {{- if .Values.fullnameOverride }}
    {{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
  {{- else }}
    {{- $name := default .Chart.Name .Values.nameOverride }}
    {{- if contains $name .Release.Name }}
      {{- .Release.Name | trunc 63 | trimSuffix "-" }}
    {{- else }}
      {{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
    {{- end }}
  {{- end }}
{{- end }}


{{- define "kube-hello-app.chart" -}}
  {{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}


{{- define "kube-hello-app.selectorLabels" -}}
app.kubernetes.io/name: {{ include "kube-hello-app.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}


{{- define "kube-hello-app.labels" -}}
helm.sh/chart: {{ include "kube-hello-app.chart" . }}
{{ include "kube-hello-app.selectorLabels" . }}
  {{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
  {{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}


{{- define "kube-hello-app.serviceAccountName" -}}
  {{- if .Values.serviceAccount.create }}
    {{- default (include "kube-hello-app.fullname" .) .Values.serviceAccount.name }}
  {{- else }}
    {{- default "default" .Values.serviceAccount.name }}
  {{- end }}
{{- end }}

{{- define "kube-hello-app.imageFullname" -}}
  {{- $tag := (required "Image tag is required" .Values.image.tag) }}
  {{- printf "%s:%s" .Values.image.repository $tag }}
{{- end }}

{{- define "kube-hello-app.mongoSecretFullname" -}}
  {{- if .Values.components.mongo.enabled }}
    {{- $secretName := (required "Mongo secretName cant be blank" .Values.components.mongo.secretName) }}
    {{- printf "%s.%s" (include "kube-hello-app.fullname" .) $secretName }}
  {{- else }}
    {{- include "kube-hello-app.fullname" . }}
  {{- end }}
{{- end }}
