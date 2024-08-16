# declaring the first input of the patient

output_file="../data/event.ics";
firstname=$1;
date=$2;
content="
BEGIN:VCALENDAR
VERSION:2.0
PRODID:-//Your Organization//Your Product//EN
BEGIN:VEVENT
UID:20240812T201943Z@yourdomain.com
DTSTAMP:20240812T201943Z
DTSTART:${date}.T100000Z
DTEND:${date}.T110000Z
SUMMARY:Expected date of demise
DESCRIPTION:${firstname}s
END:VEVENT
END:VCALENDAR ";
echo "$content" > "$output_file";

