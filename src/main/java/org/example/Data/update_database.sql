-- Drop existing foreign key constraints
ALTER TABLE ctdt_kehoachdayhoc DROP FOREIGN KEY FK_ctdt_kehoachdayhoc_ctdt_hocphan;
ALTER TABLE ctdt_kehoachmonhom DROP FOREIGN KEY FK_ctdt_kehoachmonhom_ctdt_hocphan;

-- Modify idHocPhan column type in ctdt_kehoachdayhoc
ALTER TABLE ctdt_kehoachdayhoc MODIFY COLUMN idHocPhan VARCHAR(255) NOT NULL;

-- Modify idHocPhan column type in ctdt_kehoachmonhom
ALTER TABLE ctdt_kehoachmonhom MODIFY COLUMN idHocPhan VARCHAR(255) NOT NULL;

-- Update existing data to comma-separated format
UPDATE ctdt_kehoachdayhoc SET idHocPhan = CONCAT(idHocPhan, '');
UPDATE ctdt_kehoachmonhom SET idHocPhan = CONCAT(idHocPhan, '');

-- Create new tables for many-to-many relationships
CREATE TABLE ctdt_kehoachdayhoc_hocphan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idChuyenNganh INT NOT NULL,
    idHocPhan INT NOT NULL,
    hocKyThucHien INT NOT NULL,
    FOREIGN KEY (idChuyenNganh) REFERENCES ctdt_kehoachdayhoc(idChuyenNganh),
    FOREIGN KEY (idHocPhan) REFERENCES ctdt_hocphan(idHocPhan)
);

CREATE TABLE ctdt_kehoachmonhom_hocphan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idKeHoachMonHom INT NOT NULL,
    idHocPhan INT NOT NULL,
    FOREIGN KEY (idKeHoachMonHom) REFERENCES ctdt_kehoachmonhom(id),
    FOREIGN KEY (idHocPhan) REFERENCES ctdt_hocphan(idHocPhan)
);

-- Migrate existing data
INSERT INTO ctdt_kehoachdayhoc_hocphan (idChuyenNganh, idHocPhan, hocKyThucHien)
SELECT idChuyenNganh, idHocPhan, hocKyThucHien FROM ctdt_kehoachdayhoc;

INSERT INTO ctdt_kehoachmonhom_hocphan (idKeHoachMonHom, idHocPhan)
SELECT id, idHocPhan FROM ctdt_kehoachmonhom;

-- Remove old columns
ALTER TABLE ctdt_kehoachdayhoc DROP COLUMN idHocPhan;
ALTER TABLE ctdt_kehoachmonhom DROP COLUMN idHocPhan; 