/*
MYSQL : Scheduling Errors
Write a query which displays the year in ascending order ,find for each semester how many professors are involved in delivering the session for the courses 1 to 10.

The output should contain the columns : year|semester|no_of_professor
no_of_professor: Total number of professor

*/
SELECT 
    year,
    semester,
    COUNT(DISTINCT professor_id) AS no_of_professor
FROM 
    schedule
GROUP BY 
    year, semester
ORDER BY 
    year ASC, semester ASC;
